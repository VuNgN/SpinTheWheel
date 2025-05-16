package com.vungn.luckywheeldemo

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vungn.luckywheel.WheelItem
import com.vungn.luckywheeldemo.databinding.BottomsheetNewItemBinding
import java.math.RoundingMode

class BottomSheetAddItem : BottomSheetDialogFragment {
    private lateinit var binding: BottomsheetNewItemBinding
    private var listener: AddItemListener? = null
    private var content: String = ""
    private var textColor: Int = Color.WHITE
    private var backgroundColor: Int = Color.BLACK
    private var probability: Int = 1
    private var totalSlices: Int = 0
    private var originSliceCount: Int = 1
    private var newId: Long = 0

    constructor(newId: Long, totalSlices: Int) : super() {
        this.newId = newId
        this.totalSlices = totalSlices
    }

    constructor(newId: Long, totalSlices: Int, contentLayoutId: Int) : super(contentLayoutId) {
        this.newId = newId
        this.totalSlices = totalSlices
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetNewItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.numberStepper.setValue(probability)
        binding.tvProbability.text = "${
            (probability.toFloat() / totalSlices.toFloat() * 100).toBigDecimal()
                .setScale(1, RoundingMode.UP)
        }%"
        binding.apply {
            btnAdd.setOnClickListener {
                val wheelItem = WheelItem(
                    newId, backgroundColor, textColor, content, probability
                )
                listener?.onAddItem(wheelItem)
                dismiss()
            }
            etNewItem.addTextChangedListener(onTextChanged = { text, _, _, _ ->
                content = text.toString()
            })
            bcpTextColor.listener = object : ButtonColorPicker.Listener {
                override fun onColorPicked(color: Int) {
                    textColor = color
                }
            }
            bcpBackgroundColor.listener = object : ButtonColorPicker.Listener {
                override fun onColorPicked(color: Int) {
                    backgroundColor = color
                }
            }
            numberStepper.addOnValueChangeListener(object : NumberStepper.OnValueChangeListener {
                override fun onValueChange(value: Int) {

                    val newTotalSlices = totalSlices + value - originSliceCount
                    val probability =
                        (value.toFloat() / newTotalSlices.toFloat() * 100).toBigDecimal()
                            .setScale(1, RoundingMode.UP)
                    binding.tvProbability.text = "$probability%"
                    this@BottomSheetAddItem.probability = value
                }
            })
        }
    }

    fun setListener(listener: AddItemListener) {
        this.listener = listener
    }

    interface AddItemListener {
        fun onAddItem(wheelItem: WheelItem)
    }
}