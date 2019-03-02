package ha.thanh.sidebarsmartapp

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CircleLoading(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var layoutHeight = 0
    private var layoutWidth = 0

    private var barLength = 60

    private var barWidth = 20

    private var paddingTop = 5
    private var paddingBottom = 5
    private var paddingLeft = 5
    private var paddingRight = 5

    private var barColor = -0x56000000
    private var circleColor = -0x55222223

    private val barPaint = Paint()
    private val circlePaint = Paint()
    private var circleBounds = RectF()

    private var spinSpeed = 3f
    private var delayMillis = 10
    private var progress = 0f

    private var isSpinning = false

    init {
        parseAttributes(
            context.obtainStyledAttributes(
                attrs,
                R.styleable.CircleLoading
            )
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val size: Int
        val width = measuredWidth
        val height = measuredHeight
        val widthWithoutPadding = width - getPaddingLeft() - getPaddingRight()
        val heightWithoutPadding = height - getPaddingTop() - getPaddingBottom()

        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        size = if (heightMode != View.MeasureSpec.UNSPECIFIED && widthMode != View.MeasureSpec.UNSPECIFIED) {
            if (widthWithoutPadding > heightWithoutPadding) {
                heightWithoutPadding
            } else {
                widthWithoutPadding
            }
        } else {
            Math.max(heightWithoutPadding, widthWithoutPadding)
        }

        setMeasuredDimension(
            size + getPaddingLeft() + getPaddingRight(),
            size + getPaddingTop() + getPaddingBottom()
        )
    }

    override fun onSizeChanged(newWidth: Int, newHeight: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight)
        layoutWidth = newWidth
        layoutHeight = newHeight
        setupBounds()
        setupPaints()
        invalidate()
    }

    private fun setupPaints() {

        barPaint.color = barColor
        barPaint.isAntiAlias = true
        barPaint.style = Style.STROKE
        barPaint.strokeWidth = barWidth.toFloat()

        circlePaint.color = circleColor
        circlePaint.isAntiAlias = true
        circlePaint.style = Style.STROKE
        circlePaint.strokeWidth = barWidth.toFloat()

    }

    private fun setupBounds() {

        val minValue = Math.min(layoutWidth, layoutHeight)
        val xOffset = layoutWidth - minValue
        val yOffset = layoutHeight - minValue

        paddingTop = this.getPaddingTop() + yOffset / 2
        paddingBottom = this.getPaddingBottom() + yOffset / 2
        paddingLeft = this.getPaddingLeft() + xOffset / 2
        paddingRight = this.getPaddingRight() + xOffset / 2

        val width = width
        val height = height

        circleBounds = RectF(
            (paddingLeft + barWidth).toFloat(),
            (paddingTop + barWidth).toFloat(),
            (width - paddingRight - barWidth).toFloat(),
            (height - paddingBottom - barWidth).toFloat()
        )
    }

    private fun parseAttributes(a: TypedArray) {
        barWidth = a.getDimension(R.styleable.CircleLoading_circleWidth, barWidth.toFloat()).toInt()
        spinSpeed = (a.getInt(R.styleable.CircleLoading_circleSpeed, 2000) * 2 / 3000).toFloat()
        barLength = a.getInt(R.styleable.CircleLoading_circleBarLength, barLength)

        barColor = a.getColor(R.styleable.CircleLoading_circleBarColor, barColor)
        circleColor = a.getColor(R.styleable.CircleLoading_circleColor, circleColor)

        a.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawArc(circleBounds, 360f, 360f, false, circlePaint)
        if (isSpinning) {
            canvas.drawArc(circleBounds, progress - 90, barLength.toFloat(), false, barPaint)
        } else {
            canvas.drawArc(circleBounds, -90f, progress, false, barPaint)
        }
        if (isSpinning) {
            scheduleRedraw()
        }
    }

    private fun scheduleRedraw() {
        progress += spinSpeed
        if (progress > 360) {
            progress = 0f
        }
        postInvalidateDelayed(delayMillis.toLong())
    }

    fun stopSpinning() {
        isSpinning = false
        progress = 0f
        postInvalidate()
    }

    fun startSpinning() {
        isSpinning = true
        postInvalidate()
    }

    override fun getPaddingTop(): Int {
        return paddingTop
    }

    override fun getPaddingBottom(): Int {
        return paddingBottom
    }

    override fun getPaddingLeft(): Int {
        return paddingLeft
    }

    override fun getPaddingRight(): Int {
        return paddingRight
    }

}