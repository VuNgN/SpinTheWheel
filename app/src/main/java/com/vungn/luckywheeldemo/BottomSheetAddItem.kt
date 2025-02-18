package com.vungn.luckywheeldemo

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vungn.luckywheel.WheelItem
import com.vungn.luckywheeldemo.databinding.BottomsheetNewItemBinding

class BottomSheetAddItem : BottomSheetDialogFragment {
    private lateinit var binding: BottomsheetNewItemBinding
    private var listener: AddItemListener? = null
    private var content: String = ""
    private var textColor: Int = Color.WHITE
    private var backgroundColor: Int = Color.BLACK
    private var probability: Int = 1

    constructor() : super()
    constructor(contentLayoutId: Int) : super(contentLayoutId)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetNewItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnAdd.setOnClickListener {
                val wheelItem = WheelItem(
                    backgroundColor,
                    textColor,
                    content,
                    probability
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
            etProbability.addTextChangedListener(onTextChanged = { text, _, _, _ ->
                probability = try {
                    text.toString().toInt()
                } catch (e: NumberFormatException) {
                    1
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