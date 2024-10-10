package com.example.myapp006moreactivities
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ThirdActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.acivity_third)

        val twInfo = findViewById<TextView>(R.id.twInfo)

        // Načtení dat z intentu
        val nickname = intent.getStringExtra("NICK_NAME")
        twInfo.text = "Vybraná přezdívka: $nickname"

        val btnBack = findViewById<Button>(R.id.btnBack1)
        btnBack.setOnClickListener {
            finish()
        }
        val btnSent = findViewById<Button>(R.id.btnSent)
        btnSent.setOnClickListener {
            finish()
        }
    }
}

