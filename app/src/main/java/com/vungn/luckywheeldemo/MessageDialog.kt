package com.vungn.luckywheeldemo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.vungn.luckywheeldemo.databinding.CustomMessageDialogBinding

/**
 * Custom dialog for capture action.
 * @author Nguyễn Ngọc Vũ
 */
class MessageDialog : ConstraintLayout {

    private lateinit var binding: CustomMessageDialogBinding

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet? = null) {
        binding = CustomMessageDialogBinding.inflate(LayoutInflater.from(context), this, true)
        val attr = context.obtainStyledAttributes(attrs, R.styleable.MessageDialog)
        val title = attr.getString(R.styleable.MessageDialog_title)
        val message = attr.getString(R.styleable.MessageDialog_message)
        val positiveText = attr.getString(R.styleable.MessageDialog_positive_button_text)
        val negativeText = attr.getString(R.styleable.MessageDialog_negative_button_text)
        binding.captureDialogTitle.text = title
        binding.captureDialogMessage.text = message
        binding.captureDialogPositiveButton.text = positiveText
        binding.captureDialogNegativeButton.text = negativeText
        attr.recycle()
    }

    /**
     * Set negative button text.
     * @param text Text.
     */
    fun setNegativeButtonText(text: String?) {
        if (text.isNullOrEmpty()) {
            binding.captureDialogNegativeButton.visibility = GONE
        } else {
            binding.captureDialogNegativeButton.visibility = VISIBLE
            binding.captureDialogNegativeButton.text = text
        }
    }

    /**
     * Set positive button text.
     * @param text Text.
     */
    fun setPositiveButtonText(text: String?) {
        if (text.isNullOrEmpty()) {
            binding.captureDialogPositiveButton.visibility = GONE
        } else {
            binding.captureDialogPositiveButton.visibility = VISIBLE
            binding.captureDialogPositiveButton.text = text
        }
    }

    /**
     * Hide positive button.
     */
    fun hidePositiveButton() {
        binding.captureDialogPositiveButton.visibility = GONE
    }

    /**
     * Set message for dialog.
     * @param message Message.
     */
    fun setMessage(message: String) {
        binding.captureDialogMessage.text = message
    }

    /**
     * Set message  color.
     * @param backgroundColor Color.
     */
    fun setMessageColor(textColor: Int, backgroundColor: Int) {
        binding.captureDialogMessage.setTextColor(textColor)
        binding.captureDialogMessageContainer.setBackgroundColor(backgroundColor)
    }

    /**
     * Set title for dialog.
     * @param title Title.
     */
    fun setTitle(title: String) {
        binding.captureDialogTitle.text = title
    }

    /**
     * Set listener for dialog.
     * @param listener OnCaptureDialogListener.
     */
    fun setOnMessageDialogListener(listener: OnMessageDialogListener) {
        binding.captureDialogNegativeButton.setOnClickListener {
            listener.onNegativeClick()
        }
        binding.captureDialogPositiveButton.setOnClickListener {
            listener.onPositiveClick()
        }
    }

    /**
     * Interface for dialog listener.
     */
    interface OnMessageDialogListener {
        /**
         * Handle negative click.
         */
        fun onNegativeClick()

        /**
         * Handle positive click.
         */
        fun onPositiveClick()
    }
}