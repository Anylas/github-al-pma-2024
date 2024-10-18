package com.example.myapp006moreactivities
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText


class ThirdActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.acivity_third)

        val twInfo = findViewById<TextView>(R.id.twInfo)
        val editTextInput = findViewById<EditText>(R.id.etActivity)

        // Načtení dat z intentu
        val nickname = intent.getStringExtra("NICK_NAME")
        twInfo.text = "Vybraná přezdívka: $nickname"
        val btnBack = findViewById<Button>(R.id.btnBack1)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // Ujistit se, že se vrátí na MainActivity
            startActivity(intent)
            finish() // Ukončí ThirdActivity
        }
        val btnSent = findViewById<Button>(R.id.btnSent)
        btnSent.setOnClickListener {
            val etActivity = findViewById<EditText>(R.id.etActivity)
            val etActivity = editTextInput.text.toString() // Získání textu z etActivity
            showDialog(nickname, ThirdActivity()) // Zavolání funkce pro zobrazení dialogu s textem
        }
    }

    private fun showDialog(nickname: String?, etActivity: ThirdActivity) {
        // Vytvoření AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Souhrn")
        builder.setMessage("Jméno: $nickname, Výběr aktivity: $etActivity") // Zobrazení zadaného textu
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        // Zobrazení dialogu
        val dialog = builder.create()
        dialog.show()
    }
}
