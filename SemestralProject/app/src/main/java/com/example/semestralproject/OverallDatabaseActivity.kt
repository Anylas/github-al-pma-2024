package com.example.semestralproject

import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.semestralproject.ui.horses.DatabaseHorses

class OverallDatabaseActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHorses

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.overall_database_activity)

        dbHelper = DatabaseHorses(this)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val selectedDateTextView = findViewById<TextView>(R.id.selectedDateTextView)
        val backButton = findViewById<Button>(R.id.backButton)
        val activitiesTextView = findViewById<TextView>(R.id.activitiesTextView) // Zobrazení aktivit

        // Získání aktuálního data
        val currentDate = java.util.Calendar.getInstance()
        val currentDateFormatted = "${currentDate.get(java.util.Calendar.DAY_OF_MONTH)}/${currentDate.get(java.util.Calendar.MONTH) + 1}/${currentDate.get(java.util.Calendar.YEAR)}"

        // Nastavení kalendáře na aktuální datum
        calendarView.date = currentDate.timeInMillis

        // Automatické načtení aktivit pro aktuální datum
        selectedDateTextView.text = "Vybrané datum: $currentDateFormatted"
        val activityLog = dbHelper.getActivityLogByDate(currentDateFormatted)
        if (activityLog.isNotEmpty()) {
            activitiesTextView.text = activityLog.joinToString(separator = "\n") // Zobrazení aktivit
        } else {
            activitiesTextView.text = "Žádná aktivita pro dnešní den."
        }

        // Akce při změně data v kalendáři
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            selectedDateTextView.text = "Vybrané datum: $selectedDate"

            val activityLog = dbHelper.getActivityLogByDate(selectedDate)

            if (activityLog.isNotEmpty()) {
                activitiesTextView.text = activityLog.joinToString(separator = "\n") // Zobrazení aktivit
            } else {
                activitiesTextView.text = "Žádná aktivita pro vybraný den."
                Toast.makeText(this, "Žádná aktivita pro vybraný den.", Toast.LENGTH_SHORT).show()
            }
        }

        // Akce pro tlačítko "Zpět"
        backButton.setOnClickListener {
            finish() // Vrátí se na předchozí obrazovku
        }
    }
}
