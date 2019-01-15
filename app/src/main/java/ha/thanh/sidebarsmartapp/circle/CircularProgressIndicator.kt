package ha.thanh.sidebarsmartapp.circle

import android.animation.Animator
import android.animation.PropertyValuesHolder
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import ha.thanh.sidebarsmartapp.R


class CircularProgressIndicator : View {


    private var progressPaint: Paint? = null
    private var progressBackgroundPaint: Paint? = null
    private var dotPaint: Paint? = null

    private var startAngle =
        DEFAULT_PROGRESS_START_ANGLE
    private var sweepAngle = 0

    private var circleBounds: RectF? = null

    private var radius: Float = 0.toFloat()

    private var maxProgressValue = 100.0
    var progress = 0.0
        private set

    private var isAnimationEnabled: Boolean = false

    private var isFillBackgroundEnabled: Boolean = false

    private var direction =
        DIRECTION_COUNTERCLOCKWISE

    private var progressAnimator: ValueAnimator? = null

    var onProgressChangeListener: OnProgressChangeListener? = null

    var progressColor: Int
        @ColorInt
        get() = progressPaint!!.color
        set(@ColorInt color) {
            progressPaint!!.color = color
            invalidate()
        }

    var progressBackgroundColor: Int
        @ColorInt
        get() = progressBackgroundPaint!!.color
        set(@ColorInt color) {
            progressBackgroundPaint!!.color = color
            invalidate()
        }

    val progressStrokeWidth: Float
        get() = progressPaint!!.strokeWidth

    val progressBackgroundStrokeWidth: Float
        get() = progressBackgroundPaint!!.strokeWidth


    var dotColor: Int
        @ColorInt
        get() = dotPaint!!.color
        set(@ColorInt color) {
            dotPaint!!.color = color

            invalidate()
        }

    val dotWidth: Float
        get() = dotPaint!!.strokeWidth

    var maxProgress: Double
        get() = maxProgressValue
        set(maxProgress) {
            maxProgressValue = maxProgress
            if (maxProgressValue < progress) {
                currentProgress(maxProgress)
            }
            invalidate()
        }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {

        var progressColor = Color.parseColor(DEFAULT_PROGRESS_COLOR)
        var progressBackgroundColor = Color.parseColor(DEFAULT_PROGRESS_BACKGROUND_COLOR)
        var progressStrokeWidth = dp2px(DEFAULT_STROKE_WIDTH_DP.toFloat())
        var progressBackgroundStrokeWidth = progressStrokeWidth

        var progressStrokeCap: Paint.Cap = Paint.Cap.ROUND

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs,
                R.styleable.CircularProgressIndicator
            )

            progressColor = a.getColor(R.styleable.CircularProgressIndicator_progressColor, progressColor)
            progressBackgroundColor =
                    a.getColor(R.styleable.CircularProgressIndicator_progressBackgroundColor, progressBackgroundColor)
            progressStrokeWidth = a.getDimensionPixelSize(
                R.styleable.CircularProgressIndicator_progressStrokeWidth,
                progressStrokeWidth
            )
            progressBackgroundStrokeWidth = a.getDimensionPixelSize(
                R.styleable.CircularProgressIndicator_progressBackgroundStrokeWidth,
                progressStrokeWidth
            )


            startAngle = a.getInt(
                R.styleable.CircularProgressIndicator_startAngle,
                DEFAULT_PROGRESS_START_ANGLE
            )
            if (startAngle < 0 || startAngle > 360) {
                startAngle =
                        DEFAULT_PROGRESS_START_ANGLE
            }

            isAnimationEnabled = a.getBoolean(R.styleable.CircularProgressIndicator_enableProgressAnimation, true)
            isFillBackgroundEnabled = a.getBoolean(R.styleable.CircularProgressIndicator_fillBackground, false)

            direction = a.getInt(
                R.styleable.CircularProgressIndicator_direction,
                DIRECTION_COUNTERCLOCKWISE
            )

            val cap = a.getInt(
                R.styleable.CircularProgressIndicator_progressCap,
                CAP_ROUND
            )
            progressStrokeCap = if (cap == CAP_ROUND) Paint.Cap.ROUND else Paint.Cap.BUTT

            a.recycle()
        }

        progressPaint = Paint()
        progressPaint!!.strokeCap = progressStrokeCap
        progressPaint!!.strokeWidth = progressStrokeWidth.toFloat()
        progressPaint!!.style = Paint.Style.STROKE
        progressPaint!!.color = progressColor
        progressPaint!!.isAntiAlias = true

        val progressBackgroundStyle = if (isFillBackgroundEnabled) Paint.Style.FILL_AND_STROKE else Paint.Style.STROKE
        progressBackgroundPaint = Paint()
        progressBackgroundPaint!!.style = progressBackgroundStyle
        progressBackgroundPaint!!.strokeWidth = progressBackgroundStrokeWidth.toFloat()
        progressBackgroundPaint!!.color = progressBackgroundColor
        progressBackgroundPaint!!.isAntiAlias = true

        dotPaint = Paint()
        dotPaint!!.strokeCap = Paint.Cap.ROUND
        dotPaint!!.strokeWidth = dotWidth
        dotPaint!!.style = Paint.Style.FILL_AND_STROKE
        dotPaint!!.color = dotColor
        dotPaint!!.isAntiAlias = true

        circleBounds = RectF()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        calculateBounds(w, h)
    }

    private fun calculateBounds(w: Int, h: Int) {
        radius = w / 2f
        val progressWidth = progressPaint!!.strokeWidth
        val progressBackgroundWidth = progressBackgroundPaint!!.strokeWidth
        val strokeSizeOffset =
            Math.max(
                progressWidth,
                progressBackgroundWidth
            ) // to prevent progress or dot from drawing over the bounds
        val halfOffset = strokeSizeOffset / 2f

        circleBounds!!.left = halfOffset
        circleBounds!!.top = halfOffset
        circleBounds!!.right = w - halfOffset
        circleBounds!!.bottom = h - halfOffset

        radius = circleBounds!!.width() / 2f

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        if (progressAnimator != null) {
            progressAnimator!!.cancel()
        }
    }

    override fun onDraw(canvas: Canvas) {
        drawProgressBackground(canvas)
        drawProgress(canvas)
    }

    private fun drawProgressBackground(canvas: Canvas) {
        canvas.drawArc(
            circleBounds!!, ANGLE_START_PROGRESS_BACKGROUND.toFloat(), ANGLE_END_PROGRESS_BACKGROUND.toFloat(),
            false, progressBackgroundPaint!!
        )
    }

    private fun drawProgress(canvas: Canvas) {
        canvas.drawArc(circleBounds!!, startAngle.toFloat(), sweepAngle.toFloat(), false, progressPaint!!)
    }

    fun currentProgress(currentProgress: Double) {
        if (currentProgress > maxProgressValue) {
            maxProgressValue = currentProgress
        }

        setProgress(currentProgress, maxProgressValue)
    }

    fun setProgress(current: Double, max: Double) {

        val finalAngle: Double = current / max * 360

        val oldCurrentProgress = progress

        maxProgressValue = max
        progress = Math.min(current, max)

        if (onProgressChangeListener != null) {
            onProgressChangeListener!!.onProgressChanged(progress, maxProgressValue)
        }

        stopProgressAnimation()

        if (isAnimationEnabled) {
            startProgressAnimation(oldCurrentProgress, finalAngle)
        } else {
            sweepAngle = finalAngle.toInt()
            invalidate()
        }
    }

    private fun startProgressAnimation(oldCurrentProgress: Double, finalAngle: Double) {
        val angleProperty = PropertyValuesHolder.ofInt(PROPERTY_ANGLE, sweepAngle, finalAngle.toInt())

        progressAnimator = ValueAnimator.ofObject(
            TypeEvaluator<Double> { fraction, startValue, endValue -> startValue!! + (endValue!! - startValue) * fraction },
            oldCurrentProgress,
            progress
        )
        progressAnimator!!.duration = DEFAULT_ANIMATION_DURATION.toLong()
        progressAnimator!!.setValues(angleProperty)
        progressAnimator!!.interpolator = AccelerateDecelerateInterpolator()
        progressAnimator!!.addUpdateListener { animation ->
            sweepAngle = animation.getAnimatedValue(PROPERTY_ANGLE) as Int
            invalidate()
        }
        progressAnimator!!.addListener(object : DefaultAnimatorListener() {
            override fun onAnimationCancel(animation: Animator) {
                sweepAngle = finalAngle.toInt()
                invalidate()
                progressAnimator = null
            }
        })
        progressAnimator!!.start()
    }

    private fun stopProgressAnimation() {
        if (progressAnimator != null) {
            progressAnimator!!.cancel()
        }
    }


    private fun dp2px(dp: Float): Int {
        val metrics = resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics).toInt()
    }

    interface OnProgressChangeListener {
        fun onProgressChanged(progress: Double, maxProgress: Double)
    }

    companion object {

        const val DIRECTION_COUNTERCLOCKWISE = 1

        const val CAP_ROUND = 0

        private const val DEFAULT_PROGRESS_START_ANGLE = 270
        private const val ANGLE_START_PROGRESS_BACKGROUND = 0
        private const val ANGLE_END_PROGRESS_BACKGROUND = 360

        private const val DEFAULT_PROGRESS_COLOR = "#3F51B5"
        private const val DEFAULT_STROKE_WIDTH_DP = 8
        private const val DEFAULT_PROGRESS_BACKGROUND_COLOR = "#e0e0e0"

        private const val DEFAULT_ANIMATION_DURATION = 1000

        private const val PROPERTY_ANGLE = "angle"
    }
}