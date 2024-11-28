package com.example.myapp014amynotehub.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table") //tabulka s názvem note, prostě TABULKA se záhlavím, atributy apod

data class Note( //třída ( je jedno jaká- je to Entita)
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // název sloupce val ID unikátní hodnota, ID poznámky, automaticky generované
    val title: String,  // Název poznámky
    val content: String,  // Obsah poznámky
    val categoryId: Int? = null  // Volitelný odkaz na kategorii
)
