package com.vungn.luckywheeldemo

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.vungn.luckywheel.WheelItem
import com.vungn.luckywheeldemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val wheelItems: MutableList<WheelItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        generateWheelItems()
        val adapter = RvAdapter().apply {
            items = wheelItems
            listener = object : RvAdapter.Listener {
                override fun onSpinTheWheelSuccess(wheelItem: WheelItem, autoHide: Boolean) {
                    if (autoHide) {
                        deleteItem(items.indexOf(wheelItem))
                    }
                    showResultDialog(wheelItem, autoHide || items.size <= 2) {
                        deleteItem(items.indexOf(wheelItem))
                    }
                    binding.main.setBackgroundColor(wheelItem.backgroundColor)
                }

                override fun onAddItemClick() {
                    BottomSheetAddItem().apply {
                        setListener(object : BottomSheetAddItem.AddItemListener {
                            override fun onAddItem(wheelItem: WheelItem) {
                                addItem(wheelItem)
                            }
                        })
                    }.show(supportFragmentManager, TAG)
                }

                override fun onSliceClick(position: Int, wheelItem: WheelItem) {
                    BottomSheetEditItem(position, wheelItem).apply {
                        setListener(object : BottomSheetEditItem.EditItemListener {
                            override fun onSaveItem(position: Int, wheelItem: WheelItem) {
                                updateItem(position, wheelItem)
                            }

                            override fun onDeleteItem(position: Int) {
                                if (items.size == 2) {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "You must have at least 2 items",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return
                                }
                                deleteItem(position)
                            }
                        })
                    }.show(supportFragmentManager, TAG)
                }
            }
        }
        val span = 2
        binding.main.adapter = adapter
        binding.main.apply {
            layoutManager = GridLayoutManager(
                this@MainActivity, span, GridLayoutManager.VERTICAL, false
            ).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position == 0) {
                            span
                        } else {
                            1
                        }
                    }
                }
            }
            if (itemDecorationCount == 0) {
                addItemDecoration(
                    UploadItemDecoration(
                        span, resources.getDpOfFloat(15f), true
                    )
                )
            }
        }
    }

    private fun generateWheelItems() {
        wheelItems.add(
            WheelItem(
                Color.parseColor("#6EAF62"), Color.parseColor("#FFFFFF"), "100 $"
            )
        )
        wheelItems.add(
            WheelItem(
                Color.parseColor("#5BBBD2"), Color.parseColor("#FFFFFF"), "2 $"
            )
        )
        wheelItems.add(
            WheelItem(
                Color.parseColor("#5399EC"), Color.parseColor("#FFFFFF"), "3000 $"
            )
        )
        wheelItems.add(
            WheelItem(
                Color.parseColor("#6D4CB6"), Color.parseColor("#FFFFFF"), "40 $"
            )
        )
        wheelItems.add(
            WheelItem(
                Color.parseColor("#D84E6F"), Color.parseColor("#FFFFFF"), "50000 $"
            )
        )
        wheelItems.add(
            WheelItem(
                Color.parseColor("#ED7242"), Color.parseColor("#FFFFFF"), "6000000000 $"
            )
        )
        wheelItems.add(
            WheelItem(
                Color.parseColor("#F4C647"), Color.parseColor("#FFFFFF"), "700000 $"
            )
        )
    }

    private fun showResultDialog(
        result: WheelItem,
        autoHide: Boolean,
        onPositiveClick: () -> Unit = {}
    ) {
        val dialogView = MessageDialog(this)
        dialogView.setTitle("We have a winner!")
        dialogView.setMessage(result.text)
        dialogView.setMessageColor(result.textColor, result.backgroundColor)
        dialogView.setPositiveButtonText("Hide slice")
        dialogView.setNegativeButtonText("Ok")
        if (autoHide) {
            dialogView.hidePositiveButton()
        }
        val dialog = Dialog(this)
        dialog.setContentView(dialogView)
        dialogView.setOnMessageDialogListener(object : MessageDialog.OnMessageDialogListener {
            override fun onNegativeClick() {
                dialog.dismiss()
                dialog.cancel()
            }

            override fun onPositiveClick() {
                onPositiveClick()
                dialog.dismiss()
                dialog.cancel()
            }
        })
        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        dialog.show()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}