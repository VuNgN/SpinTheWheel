package com.vungn.luckywheeldemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AlertDialog
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.flag.FlagMode
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener

class ButtonColorPicker : View, OnClickListener {
    private var _colorPickerDialog: AlertDialog? = null
    private var _colorPaint: Paint? = null
    private var _borderColorPaint: Paint? = null
    private var _strokePaint: Paint? = null
    private var _color: Int = 0
    private var _listener: Listener? = null
    val color: Int
        get() = _color

    var listener: Listener?
        get() = _listener
        set(value) {
            _listener = value
        }

    private constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context, attrs, defStyleAttr, defStyleRes
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ButtonColorPicker)
        _color = typedArray.getColor(R.styleable.ButtonColorPicker_color, 0)
        val borderColor = typedArray.getColor(R.styleable.ButtonColorPicker_border_color, 0)
        val borderWidth = typedArray.getDimension(R.styleable.ButtonColorPicker_border_width, 0f)
        typedArray.recycle()
        setOnClickListener(this)
        _colorPickerDialog =
            ColorPickerDialog.Builder(context).setTitle("ColorPicker Dialog").setPreferenceName("")
                .setPositiveButton("ok", ColorEnvelopeListener { envelope, _ ->
                    _color = envelope.color
                    _colorPaint?.color = envelope.color
                    invalidate()
                    _listener?.onColorPicked(envelope.color)
                }).setNegativeButton("cancel") { dialogInterface, _ -> dialogInterface.dismiss() }
                .attachAlphaSlideBar(true).attachBrightnessSlideBar(true).setBottomSpace(12).also {
                    val flagBubble = MyFlagView(context, R.layout.custom_color_flag)
                    flagBubble.flagMode = FlagMode.ALWAYS
                    it.colorPickerView.flagView = flagBubble
                }.create()
        _colorPaint = Paint().apply {
            color = _color
            isAntiAlias = true
        }
        _borderColorPaint = Paint().apply {
            color = Color.parseColor("#2EFFFFFF")
            isAntiAlias = true
        }
        _strokePaint = Paint().apply {
            color = borderColor
            strokeWidth = borderWidth
            style = Paint.Style.STROKE
            isAntiAlias = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val radius = width / 2f
        if (_color == Color.BLACK) {
            canvas.drawCircle(
                radius,
                radius,
                radius - (_strokePaint?.strokeWidth ?: 0f) - resources.getDpOfFloat(4f),
                _borderColorPaint!!
            )
            canvas.drawCircle(
                radius,
                radius,
                radius - (_strokePaint?.strokeWidth ?: 0f) - resources.getDpOfFloat(5f),
                _colorPaint!!
            )
        } else {
            canvas.drawCircle(
                radius,
                radius,
                radius - (_strokePaint?.strokeWidth ?: 0f) - resources.getDpOfFloat(4f),
                _colorPaint!!
            )
        }
    }

    override fun onClick(v: View?) {
        _colorPickerDialog?.show()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _colorPickerDialog?.cancel()
        _colorPickerDialog = null
    }

    fun setColor(color: Int) {
        _color = color
        _colorPaint?.color = color
        invalidate()
    }

    fun setBorderColor(color: Int) {
        _strokePaint?.color = color
        invalidate()
    }

    fun setBorderWidth(width: Float) {
        _strokePaint?.strokeWidth = width
        invalidate()
    }

    interface Listener {
        fun onColorPicked(color: Int)
    }
}