package com.example.myapp005jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExample()
        }
    }
}
/*
Tato funkce definuje samotnou Composable, což je funkce
v Jetpack Compose, která vykresluje UI.
V tomto případě bude tato funkce obsahovat
veškerou logiku a UI pro tuto jednoduchou aplikaci.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeExample() {

    /*
    První řádek inicializuje stav
    pro první textové pole (zde pro jméno).
    V Compose je důležité mít stav pro každý vstup,
    aby se mohly UI prvky aktualizovat,
    když se změní vstup uživatele.

    remember znamená, že hodnotu tohoto stavu
    si Compose pamatuje mezi změnami zobrazení (recompositions).

    mutableStateOf("") nastavuje počáteční hodnotu
    jako prázdný textový řetězec.
    Kdykoliv se stav name změní, Compose znovu vykreslí části,
    které závisí na této hodnotě.

    */

    // Stavy pro jednotlivé textové vstupy
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var place by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var devices by remember { mutableStateOf("") }

    // Přidáme Scaffold, abychom mohli přidat TopAppBar
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Moje Aplikace", color = Color.White) }, // Nastaví barvu textu na bílou
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.DarkGray,  // Nastaví pozadí na tmavě šedé
                    //titleContentColor = Color.White // Nastaví barvu textu na bílou
                )
            )
        }
    ) { innerPadding ->
        // Zbytek obsahu se vykresluje uvnitř Scaffold s paddingem
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)  // padding kolem obsahu
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Textová pole pro vstupy
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Jméno") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = surname,
                onValueChange = { surname = it },
                label = { Text("Příjmení") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Emailová adresa") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Telefonní číslo") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = age,
                onValueChange = {
                    // Omezíme vstup na číslice a kontrolujeme, že číslo není větší než 125
                    if (it.all { char -> char.isDigit() } && it.toIntOrNull()?.let { it in 0..125 } == true) {
                        age = it
                    }
                },
                label = { Text("Věk (hodnota menší než 126)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = devices,
                onValueChange = {
                    // Omezíme vstup na číslice a kontrolujeme, že číslo není větší než 5
                    if (it.all { char -> char.isDigit() } && it.toIntOrNull()?.let { it in 0..5 } == true) {
                        devices = it
                    }
                },
                label = { Text("Připojená zařízení (hodnota menší než 5)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = place,
                onValueChange = { place = it },
                label = { Text("Bydliště") },
                modifier = Modifier.fillMaxWidth()
            )

            // Tlačítka Odeslat a Vymazat
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        // dáme proměnné
                        val nameIb = name.ifBlank  { "Nikdo" }
                        val surnameIb = surname.ifBlank { "Nijaký" }
                        val emailIb = email.ifBlank { "nikdo.nijaky@android.com" }
                        val phoneIb = phone.ifBlank { "101010101" }
                        val ageIb = age.ifBlank { "100" }
                        val devicesIb = devices.ifBlank { "0" }
                        val placeIb = place.ifBlank { "České republice" }
                        resultText = "Jmenuji se $nameIb $surnameIb. Je mi $ageIb let " +
                                "a moje bydliště je $placeIb. Můj email je $emailIb " +
                                "a telefonní číslo je následující $phoneIb. Připojených zařízení " +
                                "k tomuto účtu mám celkem $devicesIb."
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Odeslat")
                }

                Button(
                    onClick = {
                        name = ""
                        surname = ""
                        age = ""
                        place = ""
                        resultText = ""
                        devices = ""
                        phone = ""
                        email = ""
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF0000),  // Hexadecimální barva pro pozadí tlačítka
                        contentColor = Color.White  // Barva textu na tlačítku
                    )
                ) {
                    Text("Vymazat")
                }
            }



            // Výsledek
            if (resultText.isEmpty()) {
                Text(
                    text = resultText,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeExample()
}

