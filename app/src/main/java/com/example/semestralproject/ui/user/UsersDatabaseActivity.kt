package com.example.semestralproject.ui.user

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.semestralproject.ui.horses.DatabaseHorses
import com.example.semestralproject.R

class UsersDatabaseActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHorses

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_database)

        dbHelper = DatabaseHorses(this)

        val usersListView = findViewById<ListView>(R.id.usersListView)
        val addUserButton = findViewById<Button>(R.id.addUsersButton)
        val backButton = findViewById<Button>(R.id.backButton)

        // Načtení seznamu uživatelů z databáze
        val users = dbHelper.getAllUsers()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, users)
        usersListView.adapter = adapter

        // Kliknutí na konkrétního uživatele pro zobrazení dialogového okna
        usersListView.setOnItemClickListener { _, _, position, _ ->
            val userName = users[position]

            // Zobrazení dialogového okna
            showUserOptionsDialog(userName)
        }

        // Přidání nového uživatele
        addUserButton.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)
        }

        // Návrat zpět
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun showUserOptionsDialog(userName: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Možnosti uživatele: $userName")
        dialogBuilder.setMessage("Chcete vymazat tohoto uživatele?")

        // Tlačítko Zpět
        dialogBuilder.setNegativeButton("Zpět") { dialog, _ ->
            dialog.dismiss()
        }

        // Tlačítko Vymazat
        dialogBuilder.setPositiveButton("Vymazat") { _, _ ->
            deleteUser(userName)
        }

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun deleteUser(userName: String) {
        val db = dbHelper.writableDatabase
        val rowsDeleted = db.delete(
            DatabaseHorses.TABLE_USERS,
            "${DatabaseHorses.COL_USER_NAME} = ?",
            arrayOf(userName)
        )

        if (rowsDeleted > 0) {
            Toast.makeText(this, "Uživatel $userName byl úspěšně smazán.", Toast.LENGTH_SHORT).show()
            refreshUserList()
        } else {
            Toast.makeText(this, "Nepodařilo se smazat uživatele.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun refreshUserList() {
        val users = dbHelper.getAllUsers() // Načtení aktuálních dat z databáze
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, users)
        findViewById<ListView>(R.id.usersListView).adapter = adapter
    }
}
