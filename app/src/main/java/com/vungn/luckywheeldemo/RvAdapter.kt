package com.vungn.luckywheeldemo

import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import com.vungn.luckywheel.LuckyWheel
import com.vungn.luckywheel.SpinTime
import com.vungn.luckywheel.WheelItem
import com.vungn.luckywheel.WheelListener
import com.vungn.luckywheel.WheelUtils
import com.vungn.luckywheeldemo.databinding.ItemHeaderBinding
import com.vungn.luckywheeldemo.databinding.ItemSliceBinding

class RvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var _listener: Listener? = null
    var listener: Listener?
        get() = _listener
        set(value) {
            _listener = value
        }
    private val _items = mutableListOf<WheelItem>()
    var items: List<WheelItem>
        get() = _items
        set(value) {
            _items.clear()
            _items.addAll(value)
            notifyDataSetChanged()
        }

    fun addItem(item: WheelItem) {
        _items.add(item)
        notifyItemInserted(_items.size - 1)
    }

    class HeaderViewHolder(private val binding: ItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var wheelItems: List<WheelItem> = emptyList()
        private val lw: LuckyWheel = binding.lwv

        fun bind(items: List<WheelItem>, listener: Listener? = null) {
            wheelItems = items
            setupLuckyWheel(listener)
            setupTextSize()
            setupSpinTime()
        }

        private fun setupLuckyWheel(listener: Listener?) {
            lw.addWheelItems(wheelItems)
            lw.setTarget(1)
            lw.setLuckyWheelReachTheTarget {
                binding.sbTextSize.isEnabled = true
                binding.sbSpinTime.isEnabled = true
                binding.tvResult.text = it.text
                listener?.onSpinTheWheelSuccess(it)
            }
            lw.setListener(object : WheelListener {
                override fun onSlideTheWheel() {
                    spinTheWheel()
                }

                override fun onTouchTheSpin() {
                    spinTheWheel()
                }
            })
        }

        private fun setupTextSize() {
            val textSizeTextView = binding.tvTextSize
            textSizeTextView.text = "15"
            var px = Math.round(
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, (15).toFloat(), itemView.resources.displayMetrics
                )
            )
            lw.setTextSize(px)
            binding.sbTextSize.apply {
                progress = 2
                max = 8
                incrementProgressBy(1)
                setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?, progress: Int, fromUser: Boolean
                    ) {
                        textSizeTextView.text = "${progress + 12}"
                        px = Math.round(
                            TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                (progress + 12).toFloat(),
                                resources.displayMetrics
                            )
                        )
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        lw.setTextSize(px)
                    }
                })
            }
        }

        private fun setupSpinTime() {
            val spinTimeTextView = binding.tvSpinTime
            spinTimeTextView.text = "3x"
            binding.sbSpinTime.apply {
                progress = 1
                max = 4
                incrementProgressBy(1)
                setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?, progress: Int, fromUser: Boolean
                    ) {
                        spinTimeTextView.text = "${progress + 1}x"
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        lw.setSpinTime(SpinTime.entries[progress])
                    }
                })
            }
        }

        private fun spinTheWheel() {
            binding.sbTextSize.isEnabled = false
            binding.sbSpinTime.isEnabled = false
            binding.tvResult.text = "Spinning..."
            val randomNum = (0..<WheelUtils.calculateTotalProbability(wheelItems)).random()
            Log.d(TAG, "onCreate: $randomNum")
            lw.rotateWheelTo(randomNum)
        }

        companion object {
            private const val TAG = "RvAdapter"
        }
    }

    class SliceViewHolder(private val binding: ItemSliceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WheelItem, listener: Listener? = null) {
            binding.main.setCardBackgroundColor(item.backgroundColor)
            binding.tvSlice.text = item.text
            binding.main.setOnClickListener {
                listener?.onSliceClick(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(ItemHeaderBinding.inflate(LayoutInflater.from(parent.context)))
            TYPE_ITEM -> SliceViewHolder(ItemSliceBinding.inflate(LayoutInflater.from(parent.context)))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return _items.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(_items, _listener)
            is SliceViewHolder -> holder.bind(_items[position - 1], _listener)
        }
    }

    interface Listener {
        fun onSpinTheWheelSuccess(wheelItem: WheelItem)

        fun onSliceClick(wheelItem: WheelItem)
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }
}