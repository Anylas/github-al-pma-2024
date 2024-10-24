package com.example.myapp06toastsnackbar

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp06toastsnackbar.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializace ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nastavení akce pro tlačítko Toast
        binding.btnShowToast.setOnClickListener {
            // Nafouknutí vlastního layoutu pro Toast
            val inflater = layoutInflater
            val layout = inflater.inflate(R.layout.toast_layout, findViewById(R.id.main))

            // Najdeme ImageView a TextView v layoutu
            val toastIcon = layout.findViewById<ImageView>(R.id.toast_icon)
            val toastText = layout.findViewById<TextView>(R.id.toast_text)

            binding.btnShowToast.setOnClickListener {
                val toast = Toast.makeText(this, "Nazdar - mám hlad", Toast.LENGTH_LONG)
                toast.show()
            }

            // Vytvoření a zobrazení vlastního Toastu
            val toast = Toast(applicationContext)
            toast.duration = Toast.LENGTH_LONG
            toast.view = layout
            toast.show()
        }


        // Nastavení akce pro tlačítko Snackbar
        binding.btnShowSnackbar.setOnClickListener {

            Snackbar.make(binding.root, "Já jsem SNACKBAR",Snackbar.LENGTH_SHORT)
                .setDuration(7000)
                .setBackgroundTint(Color.parseColor("#FF35AA"))
                .setTextColor(Color.BLACK)
                .setActionTextColor(Color.WHITE)
                .setAction("Zavřít") {
                    Toast.makeText(this, "Zavírám SNACKBAR", Toast.LENGTH_SHORT).show()
                }

                .show()
        }

    }}