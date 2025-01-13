package com.vungn.luckywheeldemo

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.skydoves.colorpickerview.AlphaTileView
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.flag.FlagView

@SuppressLint("ViewConstructor")
class MyFlagView(context: Context?, layout: Int) : FlagView(context, layout) {
    private val textView: TextView = findViewById(R.id.flag_color_code)
    private val alphaTileViewLeft: AlphaTileView = findViewById(R.id.flag_color_layout_left)
    private val alphaTileViewRight: AlphaTileView = findViewById(R.id.flag_color_layout_right)

    @SuppressLint("StringFormatInvalid")
    override fun onRefresh(colorEnvelope: ColorEnvelope?) {
        textView.text = context.getString(R.string.hex_color, colorEnvelope?.hexCode)
        colorEnvelope?.color?.let {
            alphaTileViewLeft.setPaintColor(it)
            alphaTileViewRight.setPaintColor(it)
        }
    }

    override fun onFlipped(isFlipped: Boolean?) {
        if (isFlipped == true) {
            textView.rotation = 180f
            alphaTileViewRight.visibility = VISIBLE
            alphaTileViewLeft.visibility = GONE
        } else {
            textView.rotation = 0f
            alphaTileViewRight.visibility = GONE
            alphaTileViewLeft.visibility = VISIBLE
        }
    }
}