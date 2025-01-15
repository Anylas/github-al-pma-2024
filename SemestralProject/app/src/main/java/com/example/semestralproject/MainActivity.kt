package com.example.semestralproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.semestralproject.ui.horses.DatabaseHorses
import com.example.semestralproject.ui.horses.HorsesDatabaseActivity
import com.example.semestralproject.ui.user.UsersDatabaseActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHorses

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHorses(this)

        // Inicializace tlačítek

        val horsesDatabaseButton = findViewById<Button>(R.id.horsesDatabaseButton)
        val usersDatabaseButton = findViewById<Button>(R.id.usersDatabaseButton)
        val ActivityButton = findViewById<Button>(R.id.activityButton)
        val overallDatabaseButton = findViewById<Button>(R.id.overallDatabaseButton)

        // Přechod na stránku Databáze koní
        horsesDatabaseButton.setOnClickListener {
            val intent = Intent(this, HorsesDatabaseActivity::class.java)
            startActivity(intent)
        }

        // Přechod na stránku Databáze uživatelů
        usersDatabaseButton.setOnClickListener {
            val intent = Intent(this, UsersDatabaseActivity::class.java)
            startActivity(intent)
        }
        // Přidání nové aktivity
        ActivityButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
        // Otevření databáze celkového přehledu
        overallDatabaseButton.setOnClickListener {
            val intent = Intent(this, OverallDatabaseActivity::class.java)
            startActivity(intent)
        }
    }
    }

