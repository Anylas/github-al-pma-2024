package com.example.myapp014amynotehub.data.model
import android.content.Context
import androidx.room.Room

// Tomuhle návrhovému vzoru se říká singleton - používat minimálně, hlavně pro databáze
object NoteHubDatabaseInstance {

    // Lazy inicializace instance databáze

    // Zajistí, že se hodnota INSTANCE okamžitě projeví všem vláknům,
    // což je důležité pro bezpečný přístup k jediné instanci.
    @Volatile
    private var INSTANCE: NoteHubDatabase? = null

    // Funkce pro získání instance databáze
    fun getDatabase(context: Context): NoteHubDatabase {
        // Pokud je instance null, inicializuje ji
        // ?: je víceméně "return if" - když už je instance, vrací tu samou
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                NoteHubDatabase::class.java,
                "notehub_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }

}
