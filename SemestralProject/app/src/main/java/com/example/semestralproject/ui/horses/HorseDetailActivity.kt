package com.example.semestralproject.ui.horses

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.semestralproject.R

class HorseDetailActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHorses

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.horse_details)

        dbHelper = DatabaseHorses(this)

        val horseNameTextView = findViewById<TextView>(R.id.horseNameTextView)
        val horseBoxTextView = findViewById<TextView>(R.id.horseBoxTextView)
        val horseAgeTextView = findViewById<TextView>(R.id.horseAgeTextView)
        val backButton = findViewById<Button>(R.id.backButton)

        // Získání dat o koni z Intentu
        val horseName = intent.getStringExtra("horse_name")
        val horseBox = intent.getIntExtra("horse_box", -1)
        val horseAge = intent.getIntExtra("horse_age", -1)

        // Zobrazení dat
        horseNameTextView.text = "Jméno: $horseName"
        horseBoxTextView.text = "Box: $horseBox"
        horseAgeTextView.text = "Věk: $horseAge"

        // Návrat zpět
        backButton.setOnClickListener {
            finish()
        }
        }

    }




