package com.example.vanocniaplikace017

import android.os.Bundle
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
        val imageTree = dialogView.findViewById<ImageView>(R.id.imageViewTree)
        val imagePresent = dialogView.findViewById<ImageView>(R.id.imageViewPresent)
        val imagePresent1 = dialogView.findViewById<ImageView>(R.id.imagepresent1)
        val imageUklid = dialogView.findViewById<ImageView>(R.id.imageUklid)
        val imageValasi = dialogView.findViewById<ImageView>(R.id.imageValasi)
        val imageWish = dialogView.findViewById<ImageView>(R.id.imageWish)
        val imageStedry = dialogView.findViewById<ImageView>(R.id.imageStedry)
        val quoteTextView = dialogView.findViewById<TextView>(R.id.quoteTextView)
        val playButton = dialogView.findViewById<Button>(R.id.playButton)
        val stopButton = dialogView.findViewById<Button>(R.id.stopButton)

        // Nastavení citátu
        quoteTextView.text = dayItem.content


        // Animace stromu
        if (dayItem.showTree) {
            imageTree.setBackgroundResource(R.drawable.tree_animation)
            imageTree.visibility = ImageView.VISIBLE
            val animationDrawable = imageTree.background as AnimationDrawable
            animationDrawable.start()
        } else {
            imageTree.setBackgroundResource(0) // Žádný strom, žádné pozadí
            imageTree.visibility = ImageView.GONE
        }

        // Obrázek dárečku 1
        if (dayItem.showPresent) {
            imagePresent.setBackgroundResource(R.drawable.frame22)
            imagePresent.visibility = ImageView.VISIBLE
        } else {
            imagePresent.visibility = ImageView.GONE
            imagePresent.setBackgroundResource(0) // Žádný dárek, žádné pozadí
        }

        // Obrázek dárečku 2
        if (dayItem.showPresent1) {
            imagePresent1.setBackgroundResource(R.drawable.darek1)
            imagePresent1.visibility = ImageView.VISIBLE
        } else {
            imagePresent1.visibility = ImageView.GONE
            imagePresent1.setBackgroundResource(0) // Žádný dárek, žádné pozadí
        }

        // Valaši
        if (dayItem.showValasi) {
            imageValasi.setBackgroundResource(R.drawable.imagevalasi)
            imageValasi.visibility = ImageView.VISIBLE
        } else {
            imageValasi.setBackgroundResource(0)
            imageValasi.visibility = ImageView.GONE
        }

        // Obrázek Merry X-mas
        if (dayItem.showWish) {
            imageWish.setBackgroundResource(R.drawable.imagewish)
            imageWish.visibility = ImageView.VISIBLE
        } else {
            imageWish.setBackgroundResource(0)
            imageWish.visibility = ImageView.GONE
        }

        // Obrázek úklid
        if (dayItem.showUklid) {
            imageUklid.setBackgroundResource(R.drawable.uklid)
            imageUklid.visibility = ImageView.VISIBLE
        } else {
            imageUklid.setBackgroundResource(0)
            imageUklid.visibility = ImageView.GONE
        }

        // obrázek Štedrého dne
        if (dayItem.showStedry) {
            imageStedry.setBackgroundResource(R.drawable.stedry)
            imageStedry.visibility = ImageView.VISIBLE
        } else {
            imageStedry.setBackgroundResource(0)
            imageStedry.visibility = ImageView.GONE
        }

        // Nastavení tlačítek přehrávání a zastavení
        if (dayItem.day == 2 || dayItem.day == 23 || dayItem.day == 5 || dayItem.day == 9 || dayItem.day == 15 ) {
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
            .setPositiveButton("Sdílet") { _, _ ->
                shareContent(currentDayItem?.content ?: "Sdílení obsahu není k dispozici.")
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
        val calendar = java.util.Calendar.getInstance()
        val today = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH)
        val currentMonth = calendar.get(java.util.Calendar.MONTH)
        return List(24) { day ->
            DayItem(
                day = day + 1,
                isUnlocked = if (currentMonth == java.util.Calendar.DECEMBER) {
                    // V prosinci kontrolujeme den
                    day + 1 <= today
                } else {
                    // Mimo prosinec jsou všechna okénka odemčená
                    true
                },
                content = quotes[day], // Unikátní citát pro každý den
                soundResId = when (day + 1) { // Zvuk pro konkrétní dny
                    2 -> R.raw.song1
                    5 -> R.raw.song2
                    9 -> R.raw.song3
                    15 -> R.raw.song4
                    23 -> R.raw.song5

                    else -> null
                },
                showTree = day + 1 == 22 || day + 1 == 4, // Stromek pro dny 22 a 4
                showPresent = day + 1 == 14, // Dárek pro 14 den
                showValasi = day + 1 == 2 || day + 1 == 12, // Valaši pro dny 2 a 12
                showUklid = day + 1 == 21, // Obrázek úklidu pro den 21
                showStedry = day + 1 == 24, // Obrázek štedrého dne pro den 24
                showWish = day + 1 == 15,  // Obrázek Merry X-mas pro 15 den
                showPresent1 = day + 1 == 8 || day + 1 == 3 // Dárek pro 8 a 3 den
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopMusic() // Zastaví hudbu při zničení aktivity
    }

    private fun shareContent(content: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain" // Typ obsahu: text
            putExtra(Intent.EXTRA_TEXT, content) // Obsah ke sdílení
        }
        startActivity(Intent.createChooser(shareIntent, "Sdílet pomocí")) // Nabídka výběru aplikace pro sdílení
    }
}