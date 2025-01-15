package com.example.semestralproject.ui.horses

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.semestralproject.R

class HorseAdapter(
    private val context: Context,
    private var horses: List<Horse>
) : BaseAdapter() {

    override fun getCount() = horses.size
    override fun getItem(position: Int) = horses[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.list_item_horses, parent, false
        )

        // Najděte prvky v layoutu
        val horseNameTextView = view.findViewById<TextView>(R.id.horseNameTextView)
        val infoIcon = view.findViewById<ImageView>(R.id.infoIcon)

        // Získejte data pro aktuálního koně
        val horse = getItem(position)
        horseNameTextView.text = horse.name

        // Přidejte akci pro kliknutí na ikonu
        infoIcon.setOnClickListener {
            val intent = Intent(context, HorseDetailActivity::class.java).apply {
                putExtra("horse_name", horse.name)
                putExtra("horse_box", horse.box)
                putExtra("horse_age", horse.age)
            }
            context.startActivity(intent)
        }

        return view
    }
    // Aktualizace dat
    fun updateData(newHorses: List<Horse>) {
        horses = newHorses
        notifyDataSetChanged()

    }
}
