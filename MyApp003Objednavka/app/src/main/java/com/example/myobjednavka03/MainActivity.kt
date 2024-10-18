package com.example.myobjednavka03

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RadioButtons a ImageViews pro motorky
        val radioGroup = findViewById<RadioGroup>(R.id.Motorka)
        val image1 = findViewById<ImageView>(R.id.p1)
        val image2 = findViewById<ImageView>(R.id.p2)
        val image3 = findViewById<ImageView>(R.id.p3)

        // CheckBoxy pro vybavení
        val checkBox1 = findViewById<CheckBox>(R.id.equipment1)
        val checkBox2 = findViewById<CheckBox>(R.id.equipment2)
        val checkBox3 = findViewById<CheckBox>(R.id.equipment3)

        // Souhrn objednávky
        val summaryTextView = findViewById<TextView>(R.id.summary)

        // Zobrazení obrázku při výběru motorky
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.m1 -> {
                    image1.visibility = View.VISIBLE
                    image2.visibility = View.GONE
                    image3.visibility = View.GONE
                }
                R.id.m2 -> {
                    image1.visibility = View.GONE
                    image2.visibility = View.VISIBLE
                    image3.visibility = View.GONE
                }
                R.id.m3 -> {
                    image1.visibility = View.GONE
                    image2.visibility = View.GONE
                    image3.visibility = View.VISIBLE
                }
            }
        }

        // Zobrazení souhrnu na základě zaškrtnutých checkboxů
        val buyButton = findViewById<Button>(R.id.buy)
        buyButton.setOnClickListener {
            val selectedItems = StringBuilder("Vybrané vybavení:\n")

            if (checkBox1.isChecked) selectedItems.append("- Vyhřívané rukojeti\n")
            if (checkBox2.isChecked) selectedItems.append("- Padáky\n")
            if (checkBox3.isChecked) selectedItems.append("- Něco jiného\n")

            // Zobraz souhrn
            summaryTextView.text = selectedItems.toString()
        }
    }
}