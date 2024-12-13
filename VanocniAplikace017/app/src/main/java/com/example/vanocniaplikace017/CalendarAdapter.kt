package com.example.vanocniaplikace017

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vanocniaplikace017.databinding.ItemDayBinding

class CalendarAdapter(
    private val items: List<DayItem>,
    private val onItemClick: (DayItem) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    inner class CalendarViewHolder(private val binding: ItemDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DayItem) {
            binding.tvDayNumber.text = item.day.toString()
            binding.root.isEnabled = item.isUnlocked
            binding.root.alpha = if (item.isUnlocked) 1.0f else 0.5f
            binding.root.setOnClickListener { onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = ItemDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}