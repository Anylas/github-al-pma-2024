package com.example.semestralproject.ui.user

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.semestralproject.ui.horses.DatabaseHorses
import com.example.semestralproject.R

class AddUserActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHorses

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        dbHelper = DatabaseHorses(this)

        val userNameEditText = findViewById<EditText>(R.id.userNameEditText)
        val saveUserButton = findViewById<Button>(R.id.saveUserButton)
        val backButton = findViewById<Button>(R.id.backButton)

        saveUserButton.setOnClickListener {
            val name = userNameEditText.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(this, "Vyplňte jméno uživatele.", Toast.LENGTH_SHORT).show()
            } else {
                dbHelper.addUser(name)
                Toast.makeText(this, "Uživatel $name byl přidán.", Toast.LENGTH_SHORT).show()
                finish()
            }
            }

        // Akce pro tlačítko "Zpět"
        backButton.setOnClickListener {
            finish() // Vrátí se na předchozí obrazovku
            }
        }
    }

