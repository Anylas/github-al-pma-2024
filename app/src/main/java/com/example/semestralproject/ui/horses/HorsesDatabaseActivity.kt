package com.example.semestralproject.ui.horses

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.semestralproject.R

class HorsesDatabaseActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHorses
    private lateinit var adapter: HorseAdapter // Použití vlastního adaptéru


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horses_database)

        dbHelper = DatabaseHorses(this)

        val horsesListView = findViewById<ListView>(R.id.horsesListView)
        val addHorseButton = findViewById<Button>(R.id.addHorseButton)
        val backButton = findViewById<Button>(R.id.backButton)

        // Načtení seznamu koní z databáze
        val horses = dbHelper.getAllHorses().map { name ->
            val details = dbHelper.getHorseDetailsByName(name)
            Horse(details.name, details.box, details.age)
        }

        // Nastavení vlastního adaptéru
                adapter = HorseAdapter(this, horses)
                horsesListView.adapter = adapter

        // Kliknutí na položku seznamu pro zobrazení detailu nebo jinou akci
        horsesListView.setOnItemClickListener { _, _, position, _ ->
            val horse = adapter.getItem(position)
            showHorseOptionsDialog(horse.name, horse.box) // Dialog pro možnosti
        }

        // Přidání nového koně
        addHorseButton.setOnClickListener {
            val intent = Intent(this, AddHorseActivity::class.java)
            startActivity(intent)
        }

        // Návrat zpět
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun showHorseOptionsDialog(horseName: String, horseId: Int) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_horse, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        // Najdi prvky z dialogového layoutu
        val backButton = dialogView.findViewById<Button>(R.id.dialogBackButton)
        val deleteButton = dialogView.findViewById<Button>(R.id.dialogDeleteButton)
        val message = dialogView.findViewById<TextView>(R.id.dialogMessage)

        message.text = "Chcete vymazat koně: $horseName?"

        backButton.setOnClickListener {
            dialog.dismiss()
        }

        deleteButton.setOnClickListener {
            deleteHorse(horseId)
            dialog.dismiss()
        }

        dialog.show()

        // Nastavení velikosti dialogu na menší
        val window = dialog.window
        window?.setLayout(800, 600)
    }


    private fun deleteHorse(horseId: Int) {
        val db = dbHelper.writableDatabase
        val rowsDeleted = db.delete(
            DatabaseHorses.TABLE_HORSES,
            "${DatabaseHorses.COL_HORSE_ID} = ?",
            arrayOf(horseId.toString())
        )

        if (rowsDeleted > 0) {
            Toast.makeText(this, "Kůň byl úspěšně smazán.", Toast.LENGTH_SHORT).show()
            refreshHorseList()
        } else {
            Toast.makeText(this, "Nepodařilo se smazat koně.", Toast.LENGTH_SHORT).show()
        }
    }

        private fun refreshHorseList() {
        // Načtěte aktualizovaný seznam koní
        val horses = dbHelper.getAllHorses().map { name ->
            val details = dbHelper.getHorseDetailsByName(name)
            Horse(details.name, details.box, details.age)
        }

        // Aktualizace dat v adaptér
        adapter.updateData(horses)
    }
}

