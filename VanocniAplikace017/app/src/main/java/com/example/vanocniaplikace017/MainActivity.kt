package com.example.vanocniaplikace017

import android.os.Bundle
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vanocniaplikace017.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mediaPlayer: MediaPlayer? = null // Globální MediaPlayer pro přehrávání hudby
    private lateinit var calendarItems: List<DayItem>
    private var currentDayItem: DayItem? = null // Uloží aktuálně vybraný den

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        calendarItems = generateCalendarItems()
        setContentView(binding.root)

        // Nastavení RecyclerView
        binding.recyclerView.layoutManager = GridLayoutManager(this, 4)
        binding.recyclerView.adapter = CalendarAdapter(calendarItems) { dayItem ->
            handleDayItemClick(dayItem)
        }
    }

    private fun handleDayItemClick(dayItem: DayItem) {
        if (dayItem.isUnlocked) {
            currentDayItem = dayItem // Uloží aktuálně vybraný den
            showDayContent(dayItem)
        } else {
            Toast.makeText(this, "Ještě není čas!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDayContent(dayItem: DayItem) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_content, null)
        val imageView = dialogView.findViewById<ImageView>(R.id.imageViewTree)
        val quoteTextView = dialogView.findViewById<TextView>(R.id.quoteTextView)
        val playButton = dialogView.findViewById<Button>(R.id.playButton)
        val stopButton = dialogView.findViewById<Button>(R.id.stopButton)

        // Nastavení citátu
        quoteTextView.text = dayItem.content

        // Pokud je nastaven strom, spustí animaci
        if (dayItem.showTree) {
            imageView.setBackgroundResource(R.drawable.tree_animation)
            val animationDrawable = imageView.background as AnimationDrawable
            animationDrawable.start()
        } else {
            imageView.setBackgroundResource(0) // Žádný strom, odstraníme pozadí
        }

        // Nastavení tlačítek přehrávání a zastavení
        if (dayItem.day == 2 || dayItem.day == 12) {
            playButton.visibility = Button.VISIBLE
            stopButton.visibility = Button.VISIBLE

            playButton.setOnClickListener {
                dayItem.soundResId?.let { playMusic(it) }
            }
            stopButton.setOnClickListener {
                stopMusic()
            }
        } else {
            playButton.visibility = Button.GONE
            stopButton.visibility = Button.GONE
        }

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setNeutralButton("Zavřít") { dialog, _ ->
                dialog.dismiss()
                stopMusic() // Zastaví hudbu
            }
            .create()
            .show()
    }

    private fun playMusic(soundResId: Int) {
        stopMusic() // Zastaví případnou předchozí hudbu
        Log.d("MainActivity", "Přehrávám zvuk s ID: $soundResId")
        mediaPlayer = MediaPlayer.create(this, soundResId)
        mediaPlayer?.start()

        mediaPlayer?.setOnCompletionListener {
            stopMusic()
        }
    }

    private fun stopMusic() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun generateCalendarItems(): List<DayItem> {
        val quotes = resources.getStringArray(R.array.quotes)
        val today = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH)

        return List(24) { day ->
            DayItem(
                day = day + 1,
                isUnlocked = day + 1 <= today,
                content = quotes[day], // Unikátní citát pro každý den
                soundResId = when (day + 1) { // Zvuk pro konkrétní dny
                    2 -> R.raw.song1
                    5 -> R.raw.song2
                    else -> null
                },
                showTree = day + 1 == 5 || day + 1 == 18 // Stromek pro dny 5 a 18
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopMusic() // Zastaví hudbu při zničení aktivity
    }
}