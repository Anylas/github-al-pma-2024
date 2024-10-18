package com.example.myapp001

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
    class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            val etName = findViewById<EditText>(R.id.etName)
            val etSurname = findViewById<EditText>(R.id.etSurname)
            val adres = findViewById<EditText>(R.id.adress)
            val vek = findViewById<EditText>(R.id.age)
            val infor = findViewById<TextView>(R.id.info)
            val sent = findViewById<Button>(R.id.btnSend)
            val delet = findViewById<Button>(R.id.btnDelete)
            sent.setOnClickListener {
                val n = etName.text.toString()
                val ftext =
                    "Já se jmenuji " + n + " " + etSurname.text.toString() + " a je mi " + vek.text.toString() + ". Moje Bydliště je " + adres.text.toString() + "rád vás poznávám";
                infor.text = ftext;
            }
            delet.setOnClickListener {
                etName.text.clear()
                etSurname.text.clear()
                adres.text.clear()
                vek.text.clear()
                infor.text = ""
            }

        }
    }
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
    }
}