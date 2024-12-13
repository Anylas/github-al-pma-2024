package com.example.vanocniaplikace017

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vanocniaplikace017.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val calendarItems = generateCalendarItems()
    private var mediaPlayer: MediaPlayer? = null // Globální MediaPlayer pro přehrávání hudby

    private var currentDayItem: DayItem? = null // Uloží aktuálně vybraný den

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nastavení RecyclerView
        binding.recyclerView.layoutManager = GridLayoutManager(this, 4)
        binding.recyclerView.adapter = CalendarAdapter(calendarItems) { dayItem ->
            handleDayItemClick(dayItem)
        }

        // Nastavení tlačítek Přehrát a Zastavit
        binding.playButton.setOnClickListener {
            currentDayItem?.soundResId?.let { playMusic(it) }
        }
        binding.stopButton.setOnClickListener {
            stopMusic()
        }

        // Na začátku jsou tlačítka skrytá
        hideMusicButtons()
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

        // Spustí animaci vánočního stromku
        imageView.setBackgroundResource(R.drawable.tree_animation)
        val animationDrawable = imageView.background as AnimationDrawable
        animationDrawable.start()

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Sdílet") { _, _ ->
                shareContent(dayItem.content) // Sdílení obsahu
            }
            .setNeutralButton("Zavřít") { dialog, _ ->
                dialog.dismiss()
                hideMusicButtons() // Schová tlačítka po zavření
                stopMusic() // Zastaví hudbu
            }
            .create()
            .show()

        // Zobrazí tlačítka při otevření dialogu
        showMusicButtons(dayItem)
    }

    private fun showMusicButtons(dayItem: DayItem) {
        binding.playButton.visibility = Button.VISIBLE
        binding.stopButton.visibility = Button.VISIBLE

        binding.playButton.setOnClickListener {
            dayItem.soundResId?.let { playMusic(it) }
        }
        binding.stopButton.setOnClickListener {
            stopMusic()
        }
    }

    private fun hideMusicButtons() {
        binding.playButton.visibility = Button.GONE
        binding.stopButton.visibility = Button.GONE
    }

    private fun playMusic(soundResId: Int) {
        stopMusic() // Zastaví případnou předchozí hudbu
        mediaPlayer = MediaPlayer.create(this, soundResId)
        mediaPlayer?.start()

        // Hudba se zastaví automaticky po dokončení
        mediaPlayer?.setOnCompletionListener {
            stopMusic()
        }
    }

    private fun stopMusic() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun shareContent(content: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, content)
        startActivity(Intent.createChooser(intent, "Sdílet obsah přes:"))
    }

    private fun generateCalendarItems(): List<DayItem> {
        val quotes = resources.getStringArray(R.array.quotes)
        val today = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH)

        return List(24) { day ->
            DayItem(
                day = day + 1,
                isUnlocked = day + 1 <= today,
                content = quotes[day],
                soundResId = when (day) {
                    0 -> R.raw.song1
                    1 -> R.raw.song2
                    else -> null
                }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopMusic() // Zastaví hudbu při zničení aktivity
    }
}