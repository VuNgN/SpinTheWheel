package com.vungn.luckywheeldemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class NumberStepper : View {
    private var value = 0
    private var step = 1
    private var maxValue = 0
    private var minValue = 0
    private var backgroundPaint: Paint? = null
    private var textPaint: Paint? = null
    private var buttonPaint: Paint? = null
    private var roundedCorner: Int = 0
    private var rippleDrawable: RippleDrawable? = null
    private var listeners: MutableList<OnValueChangeListener> = mutableListOf()
    private var isRtl: Boolean = false

    constructor(context: Context?) : super(context) {
        init(context, null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init(context, attrs)
    }

    constructor(
        context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(context: Context?, attrs: AttributeSet?) {
        isRtl = layoutDirection == LAYOUT_DIRECTION_RTL
        val typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.NumberStepper)
        value = typedArray.getInt(R.styleable.NumberStepper_value, 0)
        step = typedArray.getInt(R.styleable.NumberStepper_step, 1)
        maxValue = typedArray.getInt(R.styleable.NumberStepper_max_value, 100)
        minValue = typedArray.getInt(R.styleable.NumberStepper_min_value, 0)
        val backgroundColor = typedArray.getColor(R.styleable.NumberStepper_backgroundTint, 0)
        var textColor = typedArray.getColor(R.styleable.NumberStepper_textColor, 0)
        val buttonColor = typedArray.getColor(R.styleable.NumberStepper_buttonTint, 0)
        val textAppearanceResId =
            typedArray.getResourceId(R.styleable.NumberStepper_textAppearance, -1)
        typedArray.recycle()
        backgroundPaint = Paint().apply {
            color = backgroundColor
            isAntiAlias = true
        }
        textPaint = Paint().apply {
            color = textColor
            isAntiAlias = true
        }
        buttonPaint = Paint().apply {
            color = buttonColor
            isAntiAlias = true
        }
        if (textAppearanceResId != -1) {
            val textAppearance = context.obtainStyledAttributes(
                textAppearanceResId, androidx.appcompat.R.styleable.TextAppearance
            )
            if (textColor == 0) {
                textColor = textAppearance.getColor(
                    androidx.appcompat.R.styleable.TextAppearance_android_textColor, 0
                )
            }
            val textSize = textAppearance.getDimension(
                androidx.appcompat.R.styleable.TextAppearance_android_textSize, 0f
            )
            val typefaceIndex = textAppearance.getInt(
                androidx.appcompat.R.styleable.TextAppearance_android_typeface, -1
            )
            val textStyle = textAppearance.getInt(
                androidx.appcompat.R.styleable.TextAppearance_android_textStyle, -1
            )
            textAppearance.recycle()

            textPaint?.apply {
                color = textColor
                setTextSize(textSize)
                val defaultTypeface = when (typefaceIndex) {
                    1 -> Typeface.SANS_SERIF
                    2 -> Typeface.SERIF
                    3 -> Typeface.MONOSPACE
                    else -> Typeface.DEFAULT
                }
                typeface = Typeface.create(defaultTypeface, textStyle)
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        isRtl = layoutDirection == LAYOUT_DIRECTION_RTL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        roundedCorner = height / 2
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRoundRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            roundedCorner.toFloat(),
            roundedCorner.toFloat(),
            backgroundPaint!!
        )

        // Draw buttons based on RTL
        if (isRtl) {
            // Draw increment button on left
            canvas.drawCircle(height / 2f, height / 2f, height / 2f, buttonPaint!!)
            // Draw decrement button on right
            canvas.drawCircle(width - height / 2f, height / 2f, height / 2f, buttonPaint!!)
        } else {
            // Draw decrement button on left
            canvas.drawCircle(height / 2f, height / 2f, height / 2f, buttonPaint!!)
            // Draw increment button on right
            canvas.drawCircle(width - height / 2f, height / 2f, height / 2f, buttonPaint!!)
        }

        // Draw value in center
        canvas.drawText(
            value.toString(),
            width / 2f - textPaint!!.measureText(value.toString()) / 2f,
            height / 2f - (textPaint!!.descent() + textPaint!!.ascent()) / 2,
            textPaint!!
        )

        // Draw +/- symbols based on RTL
        if (isRtl) {
            // Draw + on left
            canvas.drawText(
                "+",
                height / 2f - textPaint!!.measureText("+") / 2f,
                height / 2f - (textPaint!!.descent() + textPaint!!.ascent()) / 2,
                textPaint!!
            )
            // Draw - on right
            canvas.drawText(
                "-",
                width - height / 2f - textPaint!!.measureText("-") / 2f,
                height / 2f - (textPaint!!.descent() + textPaint!!.ascent()) / 2,
                textPaint!!
            )
        } else {
            // Draw - on left
            canvas.drawText(
                "-",
                height / 2f - textPaint!!.measureText("-") / 2f,
                height / 2f - (textPaint!!.descent() + textPaint!!.ascent()) / 2,
                textPaint!!
            )
            // Draw + on right
            canvas.drawText(
                "+",
                width - height / 2f - textPaint!!.measureText("+") / 2f,
                height / 2f - (textPaint!!.descent() + textPaint!!.ascent()) / 2,
                textPaint!!
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = event.x
                if (isRtl) {
                    if (x < height) {
                        rippleDrawable?.setHotspot(x, event.y)
                        background = rippleDrawable
                        increment()
                    } else if (x > width - height) {
                        rippleDrawable?.setHotspot(x, event.y)
                        background = rippleDrawable
                        decrement()
                    }
                } else {
                    if (x < height) {
                        rippleDrawable?.setHotspot(x, event.y)
                        background = rippleDrawable
                        decrement()
                    } else if (x > width - height) {
                        rippleDrawable?.setHotspot(x, event.y)
                        background = rippleDrawable
                        increment()
                    }
                }
                invalidate()
                return true
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                background = null
            }
        }
        return super.onTouchEvent(event)
    }

    private fun increment() {
        if (value + step <= maxValue) {
            value += step
            for (listener in listeners) {
                listener.onValueChange(value)
            }
        }
    }

    private fun decrement() {
        if (value - step >= minValue) {
            value -= step
            for (listener in listeners) {
                listener.onValueChange(value)
            }
        }
    }

    fun getValue(): Int {
        return value
    }

    fun setValue(value: Int) {
        this.value = value
        invalidate()
    }

    fun addOnValueChangeListener(listener: OnValueChangeListener) {
        listeners.add(listener)
    }

    fun clearOnValueChangeListeners() {
        listeners.clear()
    }

    interface OnValueChangeListener {
        fun onValueChange(value: Int)
    }
}