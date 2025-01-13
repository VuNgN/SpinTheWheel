package com.vungn.luckywheeldemo

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vungn.luckywheel.WheelItem
import com.vungn.luckywheeldemo.databinding.BottomsheetEditItemBinding

class BottomSheetEditItem(private val position: Int, private val item: WheelItem) :
    BottomSheetDialogFragment() {
    private lateinit var binding: BottomsheetEditItemBinding
    private var listener: EditItemListener? = null
    private var content: String = ""
    private var textColor: Int = Color.WHITE
    private var backgroundColor: Int = Color.RED
    private var probability: Int = 1

    init {
        content = item.text
        textColor = item.textColor
        backgroundColor = item.backgroundColor
        probability = item.probability
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetEditItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            etNewItem.setText(item.text)
            bcpTextColor.setColor(item.textColor)
            bcpBackgroundColor.setColor(item.backgroundColor)
            etProbability.setText(item.probability.toString())
            btnSave.setOnClickListener {
                val wheelItem = WheelItem(
                    backgroundColor, textColor, content, probability
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
            etProbability.addTextChangedListener(onTextChanged = { text, _, _, _ ->
                probability = try {
                    text.toString().toInt()
                } catch (e: NumberFormatException) {
                    1
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