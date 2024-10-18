package com.example.myapp006moreactivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        val twInfo = findViewById<TextView>(R.id.twInfo)

        // Načtení dat z intentu
        val nickname = intent.getStringExtra("NICK_NAME")
        twInfo.text = "Jméno: $nickname"

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }
        val btnThird = findViewById<Button>(R.id.btnThird)
        btnThird.setOnClickListener{
            val intent = Intent(this, ThirdActivity::class.java)
            intent.putExtra("NICK_NAME", nickname)
            startActivity(intent)
        }
    }
}