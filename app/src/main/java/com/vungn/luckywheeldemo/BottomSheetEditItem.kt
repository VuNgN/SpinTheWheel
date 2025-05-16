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
import com.vungn.luckywheeldemo.databinding.BottomsheetEditItemBinding
import java.math.RoundingMode

class BottomSheetEditItem(
    private val position: Int, private val item: WheelItem, private val totalSlices: Int
) : BottomSheetDialogFragment() {
    private lateinit var binding: BottomsheetEditItemBinding
    private var listener: EditItemListener? = null
    private var content: String = ""
    private var textColor: Int = Color.WHITE
    private var backgroundColor: Int = Color.RED
    private var probability: Int = 1
    private var originSliceCount: Int = 0

    init {
        content = item.text
        textColor = item.textColor
        backgroundColor = item.backgroundColor
        probability = item.probability
        originSliceCount = item.probability
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetEditItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            etNewItem.setText(item.text)
            bcpTextColor.setColor(item.textColor)
            bcpBackgroundColor.setColor(item.backgroundColor)
            numberStepper.setValue(item.probability)
            binding.tvProbability.text = "${
                (probability.toFloat() / totalSlices.toFloat() * 100).toBigDecimal()
                    .setScale(1, RoundingMode.UP)
            }%"
            btnSave.setOnClickListener {
                val wheelItem = WheelItem(
                    item.id, backgroundColor, textColor, content, probability
                )
                listener?.onSaveItem(position, wheelItem)
                dismiss()
            }
            btnDelete.setOnClickListener {
                listener?.onDeleteItem(position)
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
                    this@BottomSheetEditItem.probability = value
                }
            })
        }
    }

    fun setListener(listener: EditItemListener) {
        this.listener = listener
    }

    interface EditItemListener {
        fun onSaveItem(position: Int, wheelItem: WheelItem)
        fun onDeleteItem(position: Int)
    }
}