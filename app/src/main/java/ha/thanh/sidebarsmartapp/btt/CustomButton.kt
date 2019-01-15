package ha.thanh.sidebarsmartapp.btt

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import ha.thanh.sidebarsmartapp.R

class CustomButton : AppCompatTextView {

    private var centerX: Float = 100f
    private var centerY: Float = 100f

    private lateinit var paint: Paint
    private lateinit var mainRect: Rect
    private lateinit var shadowRect: Rect

    private lateinit var centerRect: Rect
    private lateinit var centerGradient: GradientDrawable

    private lateinit var topLeftCornerRect: Rect
    private lateinit var topLeftCornerGradient: GradientDrawable

    private lateinit var topRect: Rect
    private lateinit var topGradient: GradientDrawable

    private lateinit var topRightCornerRect: Rect
    private lateinit var topRightCornerGradient: GradientDrawable

    private lateinit var rightRect: Rect
    private lateinit var rightGradient: GradientDrawable

    private lateinit var bottomRightCornerRect: Rect
    private lateinit var bottomRightCornerGradient: GradientDrawable

    private lateinit var bottomRect: Rect
    private lateinit var bottomGradient: GradientDrawable

    private lateinit var bottomLeftCornerRect: Rect
    private lateinit var bottomLeftCornerGradient: GradientDrawable

    private lateinit var leftRect: Rect
    private lateinit var leftGradient: GradientDrawable

    private var w: Int = 0
    private var h: Int = 0

    private var customShadowColor = 0
        set(value) {
            field = value
            init()
            calculate()
            invalidate()
        }
    private var customShadowRadius = 100f
        set(value) {
            field = value
            init()
            calculate()
            invalidate()
        }
    private var xOffset = 0f
        set(value) {
            field = value
            init()
            calculate()
            invalidate()
        }
    private var yOffset = 0f
        set(value) {
            field = value
            init()
            calculate()
            invalidate()
        }
    private var shadowHeight = 1f
        set(value) {
            field = value
            init()
            calculate()
            invalidate()
        }
    private var shadowWidth = 1f
        set(value) {
            field = value
            init()
            calculate()
            invalidate()
        }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        getAttributes(attributeSet)
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttrs: Int) : super(
        context,
        attributeSet,
        defStyleAttrs
    ) {
        getAttributes(attributeSet)
        init()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        this.w = w
        this.h = h
        calculate()
    }

    private fun calculate() {
        centerX = (w / 2).toFloat()
        centerY = (h / 2).toFloat()
        mainRect = Rect(0, 0, w, h)

        with(customShadowRadius) {
            centerRect = Rect(
                0, 0, (width - customShadowRadius * 2
                        - ((1 - shadowWidth) * width)).toInt(), (height - customShadowRadius * 2
                        - (1 - shadowHeight) * height).toInt()
            )

            topLeftCornerRect = Rect(0, 0, toInt(), toInt())
            topRect = Rect(
                0, 0, (width - customShadowRadius * 2
                        - ((1 - shadowWidth) * width)).toInt(), toInt()
            )

            topRightCornerRect = Rect(0, 0, toInt(), toInt())
            rightRect = Rect(
                0, 0, toInt(), (height - customShadowRadius * 2
                        - (1 - shadowHeight) * height).toInt()
            )

            bottomRightCornerRect = Rect(0, 0, toInt(), toInt())
            bottomRect = Rect(
                0, 0, (width - customShadowRadius * 2
                        - ((1 - shadowWidth) * width)).toInt(), toInt()
            )

            bottomLeftCornerRect = Rect(0, 0, toInt(), toInt())
            leftRect = Rect(
                0, 0, toInt(), (height - customShadowRadius * 2
                        - (1 - shadowHeight) * height).toInt()
            )
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.also {
            // draw shadow
            drawCenter(it)
            drawTopLeftCorner(it)
            drawTop(it)
            drawTopRightCorner(it)
            drawRight(it)
            drawBottomRightCorner(it)
            drawBottom(it)
            drawBottomLeftCorner(it)
            drawLeft(it)
            // draw main content
            drawButtonBackground(it)
        }
        super.onDraw(canvas)
    }

    private fun init() {

        paint = Paint().apply {
            color = Color.BLUE
            style = Paint.Style.FILL_AND_STROKE
        }

        mainRect = Rect()
        shadowRect = Rect()

        initGradient()
    }

    private fun getAttributes(attr: AttributeSet) {

        with(context.obtainStyledAttributes(attr, R.styleable.CustomButton)) {
            customShadowRadius = getDimension(
                R.styleable.CustomButton_shadowRadius,
                (2 * resources.displayMetrics.density)
            )
            customShadowColor = getColor(
                R.styleable.CustomButton_shadowColor,
                ContextCompat.getColor(context, R.color.gray_20)
            )

            xOffset = getFloat(R.styleable.CustomButton_shadowXOffset, 0f)
            yOffset = getFloat(R.styleable.CustomButton_shadowYOffset, 0f)

            shadowHeight = getFloat(R.styleable.CustomButton_shadowPercentHeight, 1f)
            shadowWidth = getFloat(R.styleable.CustomButton_shadowPercentWidth, 1f)

            recycle()
        }

        background = background ?: ContextCompat.getDrawable(
            context,
            R.drawable.default_background
        )
    }


    private fun initGradient() {
        initCenter()
        initTopLeftCorner()
        initTop()
        initTopRightCorner()
        initRight()
        initBottomRightCorner()
        initBottom()
        initBottomLeftCorner()
        initLeft()
    }

    private fun initCenter() {
        centerGradient = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(customShadowColor, customShadowColor)
        ).apply {
            shape = GradientDrawable.RECTANGLE
            gradientType = GradientDrawable.RADIAL_GRADIENT
            gradientRadius = customShadowRadius
            setGradientCenter(1f, 1f)
        }
    }

    private fun initTopLeftCorner() {
        topLeftCornerGradient = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(customShadowColor, Color.TRANSPARENT)
        ).apply {
            shape = GradientDrawable.RECTANGLE
            gradientType = GradientDrawable.RADIAL_GRADIENT
            gradientRadius = customShadowRadius
            setGradientCenter(1f, 1f)
        }
    }

    private fun initTop() {
        topGradient = GradientDrawable(
            GradientDrawable.Orientation.BOTTOM_TOP,
            intArrayOf(customShadowColor, Color.TRANSPARENT)
        ).apply {
            shape = GradientDrawable.RECTANGLE
            gradientType = GradientDrawable.LINEAR_GRADIENT
            gradientRadius = customShadowRadius
        }
    }

    private fun initTopRightCorner() {
        topRightCornerGradient = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(customShadowColor, Color.TRANSPARENT)
        ).apply {
            shape = GradientDrawable.RECTANGLE
            gradientType = GradientDrawable.RADIAL_GRADIENT
            gradientRadius = customShadowRadius
            setGradientCenter(0f, 1f)
        }
    }

    private fun initRight() {
        rightGradient = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(customShadowColor, Color.TRANSPARENT)
        ).apply {
            shape = GradientDrawable.RECTANGLE
            gradientType = GradientDrawable.LINEAR_GRADIENT
            gradientRadius = customShadowRadius
        }
    }

    private fun initBottomRightCorner() {
        bottomRightCornerGradient = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(customShadowColor, Color.TRANSPARENT)
        ).apply {
            shape = GradientDrawable.RECTANGLE
            gradientType = GradientDrawable.RADIAL_GRADIENT
            gradientRadius = customShadowRadius
            setGradientCenter(0f, 0f)
        }
    }

    private fun initBottom() {
        bottomGradient = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(customShadowColor, Color.TRANSPARENT)
        ).apply {
            shape = GradientDrawable.RECTANGLE
            gradientType = GradientDrawable.LINEAR_GRADIENT
            gradientRadius = customShadowRadius
        }
    }

    private fun initBottomLeftCorner() {
        bottomLeftCornerGradient = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(customShadowColor, Color.TRANSPARENT)
        ).apply {
            shape = GradientDrawable.RECTANGLE
            gradientType = GradientDrawable.RADIAL_GRADIENT
            gradientRadius = customShadowRadius
            setGradientCenter(1f, 0f)
        }
    }

    private fun initLeft() {
        leftGradient = GradientDrawable(
            GradientDrawable.Orientation.RIGHT_LEFT,
            intArrayOf(customShadowColor, Color.TRANSPARENT)
        ).apply {
            shape = GradientDrawable.RECTANGLE
            gradientType = GradientDrawable.LINEAR_GRADIENT
            gradientRadius = customShadowRadius
        }
    }


    private fun drawCenter(canvas: Canvas) {
        val percentX = (1 - shadowWidth) * width / 2
        val percentY = (1 - shadowHeight) * height / 2

        with(canvas) {
            save()

            centerGradient.bounds = centerRect

            translate(
                customShadowRadius + xOffset + percentX,
                customShadowRadius + yOffset + percentY
            )

            centerGradient.draw(this)

            restore()
        }
    }

    private fun drawTopLeftCorner(canvas: Canvas) {
        val percentX = (1 - shadowWidth) * width / 2
        val percentY = (1 - shadowHeight) * height / 2

        with(canvas) {
            save()

            topLeftCornerGradient.bounds = topLeftCornerRect

            translate(xOffset + percentX, +yOffset + percentY)

            topLeftCornerGradient.draw(this)

            restore()
        }
    }

    private fun drawTop(canvas: Canvas) {
        val percentX = (1 - shadowWidth) * width / 2
        val percentY = (1 - shadowHeight) * height / 2

        with(canvas) {
            save()

            topGradient.bounds = topRect

            translate(
                customShadowRadius + xOffset + percentX,
                +yOffset + percentY
            )

            topGradient.draw(this)

            restore()
        }
    }

    private fun drawTopRightCorner(canvas: Canvas) {
        val percentX = (1 - shadowWidth) * width / 2
        val percentY = (1 - shadowHeight) * height / 2

        with(canvas) {
            save()

            topRightCornerGradient.bounds = topRightCornerRect

            translate(
                width - customShadowRadius + xOffset - percentX,
                +yOffset + percentY
            )

            topRightCornerGradient.draw(this)

            restore()
        }
    }

    private fun drawRight(canvas: Canvas) {
        val percentX = (1 - shadowWidth) * width / 2
        val percentY = (1 - shadowHeight) * height / 2

        with(canvas) {
            save()

            rightGradient.bounds = rightRect

            translate(
                width - customShadowRadius + xOffset - percentX,
                customShadowRadius + yOffset + percentY
            )

            rightGradient.draw(this)

            restore()
        }
    }

    private fun drawBottomRightCorner(canvas: Canvas) {
        val percentX = (1 - shadowWidth) * width / 2
        val percentY = (1 - shadowHeight) * height / 2

        with(canvas) {
            save()

            bottomRightCornerGradient.bounds = bottomRightCornerRect

            translate(
                width - customShadowRadius + xOffset - percentX,
                height - customShadowRadius + yOffset - percentY
            )

            bottomRightCornerGradient.draw(this)

            restore()
        }
    }

    private fun drawBottom(canvas: Canvas) {
        val percentX = (1 - shadowWidth) * width / 2
        val percentY = (1 - shadowHeight) * height / 2

        with(canvas) {
            save()

            bottomGradient.bounds = bottomRect

            translate(
                customShadowRadius + xOffset + percentX,
                height - customShadowRadius + yOffset - percentY
            )

            bottomGradient.draw(this)

            restore()
        }
    }

    private fun drawBottomLeftCorner(canvas: Canvas) {
        val percentX = (1 - shadowWidth) * width / 2
        val percentY = (1 - shadowHeight) * height / 2

        with(canvas) {
            save()

            bottomLeftCornerGradient.bounds = bottomLeftCornerRect

            translate(
                +xOffset + percentX,
                height - customShadowRadius + yOffset - percentY
            )

            bottomLeftCornerGradient.draw(this)

            restore()
        }
    }

    private fun drawLeft(canvas: Canvas) {
        val percentX = (1 - shadowWidth) * width / 2
        val percentY = (1 - shadowHeight) * height / 2

        with(canvas) {
            save()

            leftGradient.bounds = leftRect

            translate(
                +xOffset + percentX,
                customShadowRadius + yOffset + percentY
            )

            leftGradient.draw(this)

            restore()
        }
    }

    private fun drawButtonBackground(canvas: Canvas) {
        with(canvas) {
            save()
            background.draw(this)
            restore()
        }
    }

}