package com.example.semestralproject.ui.horses

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.semestralproject.R

class AddHorseActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHorses

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_horse)

        dbHelper = DatabaseHorses(this)

        val horseNameEditText = findViewById<EditText>(R.id.horseNameEditText)
        val horseBoxEditText = findViewById<EditText>(R.id.horseBoxEditText)
        val horseAgeEditText = findViewById<EditText>(R.id.horseAgeEditText)
        val saveHorseButton = findViewById<Button>(R.id.saveHorseButton)
        val backButton = findViewById<Button>(R.id.backButton)

        saveHorseButton.setOnClickListener {
            val name = horseNameEditText.text.toString()
            val box = horseBoxEditText.text.toString().toIntOrNull()
            val age = horseAgeEditText.text.toString().toIntOrNull()

            if (name.isEmpty() || box == null || age == null) {
                Toast.makeText(this, "Vyplňte všechna pole správně.", Toast.LENGTH_SHORT).show()
            } else {
                dbHelper.addHorse(name, box, age)
                Toast.makeText(this, "Kůň $name byl přidán.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        // Akce pro tlačítko "Zpět"
        backButton.setOnClickListener {
            finish() // Vrátí se na předchozí obrazovku
        }
    }
}
