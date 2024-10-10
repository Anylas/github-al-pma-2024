package com.example.myapp005jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapp005jetpackcompose.ui.theme.MyApp005JetpackComposeTheme

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
fun ComposeExample () {
// Přidáme Scaffold, abychom mohli přidat TopAppBar
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Moje Aplikace", color = Color.White) }, // Nastaví barvu textu na bílou
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.DarkGray,  // Nastaví pozadí na černé
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
}
) {


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeExample()
}
    }
}
