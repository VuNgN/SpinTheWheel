package com.vungn.luckywheeldemo

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
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
                override fun onSpinTheWheelSuccess(wheelItem: WheelItem) {
                    binding.main.setBackgroundColor(wheelItem.backgroundColor)
                }

                override fun onSliceClick(wheelItem: WheelItem) {

                }
            }
        }
        val span = 2
        binding.main.adapter = adapter
        binding.main.apply {
            layoutManager = GridLayoutManager(
                this@MainActivity,
                span,
                GridLayoutManager.VERTICAL,
                false
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
                Color.parseColor("#F8C448"), Color.parseColor("#FFFFFF"), "100 $", 2
            )
        )
        wheelItems.add(
            WheelItem(
                Color.parseColor("#39A1E8"), Color.parseColor("#FFFFFF"), "2 $", 4
            )
        )
        wheelItems.add(
            WheelItem(
                Color.parseColor("#C13DCA"), Color.parseColor("#FFFFFF"), "30 $"
            )
        )
        wheelItems.add(
            WheelItem(
                Color.parseColor("#87D657"), Color.parseColor("#FFFFFF"), "4000 $"
            )
        )
        wheelItems.add(
            WheelItem(
                Color.parseColor("#0DCC6A"), Color.parseColor("#FFFFFF"), "50000000000000 $"
            )
        )
        wheelItems.add(
            WheelItem(
                Color.parseColor("#2FAF74"), Color.parseColor("#FFFFFF"), "60 $"
            )
        )
        wheelItems.add(
            WheelItem(
                Color.parseColor("#E65051"),
                Color.parseColor("#FFFFFF"),
                "700 2000 20000 3000000 30"
            )
        )
    }

    private fun Resources.getDpOfFloat(dp: Float): Int {
        return Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, this.displayMetrics
            )
        )
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}