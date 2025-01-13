package com.example.semestralproject

import android.os.Bundle
import android.widget.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.semestralproject.ui.horses.DatabaseHorses
import java.util.Calendar

class SecondActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHorses

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        dbHelper = DatabaseHorses(this)

        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val activityDescription = findViewById<EditText>(R.id.activityDescription)
        val saveButton = findViewById<Button>(R.id.saveButton)

        val horseNameButton = findViewById<Button>(R.id.selectHorseButton)
        val userNameButton = findViewById<Button>(R.id.selectUserButton)
        val backButton = findViewById<Button>(R.id.backButton)

        // Získání aktuálního dne
        val currentDate = Calendar.getInstance()

        // Nastavení minimálního a maximálního data
        val minDate = (currentDate.clone() as Calendar).apply {
            add(Calendar.DAY_OF_MONTH, -2) // 2 dny zpět
        }
        val maxDate = (currentDate.clone() as Calendar).apply {
            add(Calendar.DAY_OF_MONTH, 1) // dnešní datum
        }

        // Nastavení omezení pro DatePicker
        datePicker.minDate = minDate.timeInMillis
        datePicker.maxDate = maxDate.timeInMillis

        saveButton.setOnClickListener {
            val description = activityDescription.text.toString()
            val selectedDate = Calendar.getInstance().apply {
                set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
            }

            val selectedHorse = horseNameButton.text.toString()
            val selectedUser = userNameButton.text.toString()

            // Kontrola, zda je vybrané datum platné
            if (selectedDate.before(minDate) || selectedDate.after(maxDate)) {
                Toast.makeText(
                    this,
                    "Vyber datum mezi dnešním dnem a maximálně 2 dny zpět.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (description.isBlank() || selectedHorse == "Vyber koně" || selectedUser == "Vyber jezdce") {
                Toast.makeText(this, "Všechny údaje musí být vyplněny!", Toast.LENGTH_SHORT).show()
            } else {
                val success = dbHelper.saveActivity(
                    "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}",
                    description,
                    selectedHorse,
                    selectedUser
                )
                if (success) {
                    Toast.makeText(this, "Aktivita úspěšně uložena!", Toast.LENGTH_LONG).show()

                    // Aktualizuj zobrazení dat (pokud je potřeba)
                    activityDescription.text.clear()
                    horseNameButton.text = "Vyber koně"
                    userNameButton.text = "Vyber jezdce"

                    // Nastavení aktuálního data jako výchozího v kalendáři
                    datePicker.updateDate(
                        currentDate.get(Calendar.YEAR),
                        currentDate.get(Calendar.MONTH),
                        currentDate.get(Calendar.DAY_OF_MONTH)
                    )
                } else {
                    Toast.makeText(this, "Nepodařilo se uložit aktivitu. Zkontroluj data.", Toast.LENGTH_LONG).show()
                }
            }
        }

        // Akce pro tlačítko "Zpět"
        backButton.setOnClickListener {
            finish() // Vrátí se na předchozí obrazovku
        }

        // Výběr koní a uživatelů
        horseNameButton.setOnClickListener {
            val horseNamesDialog = AlertDialog.Builder(this)
                .setTitle("Vyber koníka")
                .setItems(dbHelper.getAllHorses().toTypedArray()) { _, which ->
                    horseNameButton.text = dbHelper.getAllHorses()[which]
                }
                .create()
            horseNamesDialog.show()
        }

        userNameButton.setOnClickListener {
            val userNamesDialog = AlertDialog.Builder(this)
                .setTitle("Vyber jezdce")
                .setItems(dbHelper.getAllUsers().toTypedArray()) { _, which ->
                    userNameButton.text = dbHelper.getAllUsers()[which]
                }
                .create()
            userNamesDialog.show()
        }
    }
}
