package com.example.myapp011adastore

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapp011adatastore.databinding.ActivityMainBinding
import com.example.myapp011adatastore.getUser
import com.example.myapp011adatastore.saveUser
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializace bindingu
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Uložení dat při kliknutí na tlačítko "Uložit"
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val ageString = binding.etAge.text.toString().trim()

            if (ageString.isBlank()) {
                Toast.makeText(this, "Hele, vyplň věk...", Toast.LENGTH_SHORT).show()
            } else {
                val age = ageString.toInt()
                val isAdult = binding.cbAdult.isChecked
                if ((age < 18 && isAdult) || (age >= 18 && !isAdult)) {
                    Toast.makeText(this, "Kecáš, takže nic ukládat nebudu", Toast.LENGTH_SHORT).show()
                } else {
                    lifecycleScope.launch {
                        applicationContext.saveUser(name, age, isAdult)
                        Toast.makeText(this@MainActivity, "Jasně, ukládám...", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Načítání dat při kliknutí na tlačítko "Načíst"
        binding.btnLoad.setOnClickListener {
            lifecycleScope.launch {
                applicationContext.getUser().collect { user ->
                    binding.etName.setText(user.name)
                    binding.etAge.setText(user.age.toString())
                    binding.cbAdult.isChecked = user.isAdult
                    Toast.makeText(this@MainActivity, "Načteno.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
