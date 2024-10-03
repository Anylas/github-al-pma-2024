package com.example.myobjednavka03

import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myobjednavka03.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Objednávka motorky"

        binding.buy.setOnClickListener {
            val RadioGroup= findViewById<RadioGroup>(R.id.Motorka)
            val p1_= findViewById<RadioButton>(R.id.m1)
            val p2_= findViewById<RadioButton>(R.id.m2)
            val p3_= findViewById<RadioButton>(R.id.m3)
            val checkBox1 = binding.equipment1.isChecked
            val checkBox2 = binding.equipment2.isChecked
            val checkBox3 = binding.equipment3.isChecked
            val img1 = findViewById<ImageView>(R.id.p1)
            val img2 = findViewById<ImageView>(R.id.p2)
            val img3 = findViewById<ImageView>(R.id.p3)
            val obejdnavkaText = "Souhr objednávky" +
                    "${summary.text}" +
                    (if(checkBox1)" Vyhřívané rukojeti" else "") +
                    (if(checkBox2)" Padáky" else "") +
                    (if(checkBox3)" Něco jiného" else "")
            binding.souhrnobjednávky.text= objednávkaText
        }
        //změna obrázku v závislosti na vybraném radioButtonu
        binding.p1.setOnClickListener {
            binding.p1.setImageResource(R.drawable.image1)
        }
        binding.p2.setOnClickListener {
            binding.p2.setImageResource(R.drawable.image2)
        }
        binding.p3.setOnClickListener {
            binding.p3.setImageResource(R.drawable.image3)
        }
    }
}

        //načtení ID vybraného radioButtonu z radioGroup

