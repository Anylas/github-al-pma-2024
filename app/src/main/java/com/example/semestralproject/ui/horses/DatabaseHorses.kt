package com.example.semestralproject.ui.horses
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Třída WeatherDatabase
 * Přístup k SQLite databázi,
 * Ukládá aktuální počasí, předpovědí a oblíbená města.
 *
 * @constructor instance.
 * @param context otevření/vytvoření databáze.
 */


class DatabaseHorses(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "DatabaseHorses.db"
        const val DATABASE_VERSION = 1
        const val COL_ID = "ID"

        //tabulka pro databázi koní a jejich informace
        const val TABLE_HORSES = "Horses"
        const val COL_HORSE_ID = "HorseID"
        const val COL_HORSE_NAME = "HorseName"
        const val COL_HORSE_BOX = "HorseBox"
        const val COL_HORSE_AGE = "HorseAge"

        //tabulka pro databázi uživatelů a jejich informace
        const val TABLE_USERS = "Users"
        const val COL_USER_ID = "UserID"
        const val COL_USER_NAME = "UserName"

        //tabulka pro databázi aktivit a jejich informace
        const val TABLE_ACTIVITY = "ActivityLog"
        const val COL_ACTIVITY_ID = "ActivityID"
        const val COL_ACTIVITY_DATE = "ActivityDate"
        const val COL_ACTIVITY_DESCRIPTION = "ActivityDescription"
        const val COL_ACTIVITY_HORSE_ID = "HorseID_FK"
        const val COL_ACTIVITY_USER_ID = "UserID_FK"
    }

    /**
     * Metoda volaná při prvním vytvoření databáze.
     * Vytváří tabulky pro ukládání aktuální tabulky s informacemi o každém koni, o uživateli a o aktivitě.
     */
    override fun onCreate(db: SQLiteDatabase?) {
        //vytvoření tabulky pro každého koně s informacemi
        val createHorsesTable = """
            CREATE TABLE $TABLE_HORSES (
            $COL_HORSE_ID INTEGER PRIMARY KEY AUTOINCREMENT, -- Přidání primárního klíče
                $COL_HORSE_NAME TEXT NOT NULL,
                $COL_HORSE_BOX INTEGER NOT NULL,
                $COL_HORSE_AGE INTEGER NOT NULL
            )
        """

        //vytvoření tabulky pro uživatele s informacemi
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COL_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_USER_NAME TEXT NOT NULL
            )
        """

        //vytvoření tabulky o aktivitách s informacemi
        val createActivityTable = """
            CREATE TABLE $TABLE_ACTIVITY (
                $COL_ACTIVITY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_ACTIVITY_DATE TEXT NOT NULL,
                $COL_ACTIVITY_DESCRIPTION TEXT,
                $COL_ACTIVITY_HORSE_ID INTEGER,
                $COL_ACTIVITY_USER_ID INTEGER,
                FOREIGN KEY($COL_ACTIVITY_HORSE_ID) REFERENCES $TABLE_HORSES($COL_HORSE_ID),
                FOREIGN KEY($COL_ACTIVITY_USER_ID) REFERENCES $TABLE_USERS($COL_USER_ID)
            )
        """
        db?.execSQL(createHorsesTable)
        db?.execSQL(createUsersTable)
        db?.execSQL(createActivityTable)

        insertInitialHorses(db)
        insertInitialUsers(db)
    }

    /**Tato funkce vkládá pevně dané informace o koních do tabulky Horses (jméno, číslo boxu, věk)
    * Kód definuje pole koní pomocí Triple, což je datová struktura Kotlinu, která uchovává tři hodnoty v jednom objektu.
    */

    private fun insertInitialHorses(db: SQLiteDatabase?) {
        val horses = arrayOf(
            Triple("Dia", 1, 12),
            Triple("Anita", 2, 13),
            Triple("Mario", 3, 14),
            Triple("Chuli", 4, 15),
            Triple("Daisy", 5, 16),
            Triple("Denver", 6, 17),
            Triple("Dona", 7, 18),
            Triple("Dom", 8, 19),
            Triple("Amor", 9, 14),
            Triple("Gina", 10, 12)
        )
        horses.forEachIndexed { index, horse ->
            val values = ContentValues().apply {
                put(COL_HORSE_NAME, horse.first) //jméno koně
                put(COL_HORSE_BOX, horse.second) //číslo boxu koně
                put(COL_HORSE_AGE, horse.third) //věk koně
            }
            db?.insert(TABLE_HORSES, null, values)
        }
    }

    private fun insertInitialUsers(db: SQLiteDatabase?) {
        val users = listOf("Alice", "Sonia", "Anet", "Diana", "Sára", "Veronika", "Monika", "Hanka", "Iva", "Jana")
        users.forEach { user ->
            val values = ContentValues().apply {
                put(COL_USER_NAME, user)
            }
            db?.insert(TABLE_USERS, null, values)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_HORSES")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ACTIVITY")
        onCreate(db)
    }

    fun addHorse(name: String, box: Int, age: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_HORSE_NAME, name)
            put(COL_HORSE_BOX, box)
            put(COL_HORSE_AGE, age)
        }
        db.insert(TABLE_HORSES, null, values)
        db.close()
    }

    fun getAllHorses(): List<String> {
        val horses = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT $COL_HORSE_NAME FROM $TABLE_HORSES", null)
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(0) // Získá HorseName
                horses.add(name)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return horses
    }
    fun getHorseDetailsByName(name: String): Horse {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COL_HORSE_NAME, $COL_HORSE_BOX, $COL_HORSE_AGE FROM $TABLE_HORSES WHERE $COL_HORSE_NAME = ?",
            arrayOf(name)
        )

        var horse = Horse("", 0, 0)
        if (cursor.moveToFirst()) {
            val horseName = cursor.getString(0)
            val horseBox = cursor.getInt(1)
            val horseAge = cursor.getInt(2)
            horse = Horse(horseName, horseBox, horseAge)
        }
        cursor.close()
        db.close()
        return horse
    }



    fun addUser(name: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_USER_NAME, name)
        }
        db.insert(TABLE_USERS, null, values)
        db.close()
    }

    fun getAllUsers(): List<String> {
        val users = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT $COL_USER_NAME FROM $TABLE_USERS", null)
        if (cursor.moveToFirst()) {
            do {
                users.add(cursor.getString(0)) // Získá jméno uživatele
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return users
    }
    fun getActivityLogByDate(date: String): List<String> {
        val activities = mutableListOf<String>()
        val db = readableDatabase

        val query = """
        SELECT a.$COL_ACTIVITY_DATE, u.$COL_USER_NAME, h.$COL_HORSE_NAME, a.$COL_ACTIVITY_DESCRIPTION
        FROM $TABLE_ACTIVITY a
        JOIN $TABLE_USERS u ON a.$COL_ACTIVITY_USER_ID = u.$COL_USER_ID
        JOIN $TABLE_HORSES h ON a.$COL_ACTIVITY_HORSE_ID = h.$COL_HORSE_ID
        WHERE a.$COL_ACTIVITY_DATE = ?
    """
        val cursor = db.rawQuery(query, arrayOf(date))

        if (cursor.moveToFirst()) {
            do {
                val userName = cursor.getString(1)
                val horseName = cursor.getString(2)
                val description = cursor.getString(3)
                activities.add("Kdo byl: $userName,s kým: $horseName- $description")
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return activities
    }
    fun saveActivity(date: String, description: String, horseName: String, userName: String): Boolean {
        val db = writableDatabase

        // Získat ID koně podle jména
        val horseIdQuery = "SELECT $COL_HORSE_ID FROM $TABLE_HORSES WHERE $COL_HORSE_NAME = ?"
        val horseCursor = db.rawQuery(horseIdQuery, arrayOf(horseName))
        if (!horseCursor.moveToFirst()) {
            horseCursor.close()
            db.close()
            return false // Kůň nebyl nalezen
        }
        val horseId = horseCursor.getInt(0)
        horseCursor.close()

        // Získat ID uživatele podle jména
        val userIdQuery = "SELECT $COL_USER_ID FROM $TABLE_USERS WHERE $COL_USER_NAME = ?"
        val userCursor = db.rawQuery(userIdQuery, arrayOf(userName))
        if (!userCursor.moveToFirst()) {
            userCursor.close()
            db.close()
            return false // Uživatel nebyl nalezen
        }
        val userId = userCursor.getInt(0)
        userCursor.close()

        // Uložit aktivitu
        val values = ContentValues().apply {
            put(COL_ACTIVITY_DATE, date)
            put(COL_ACTIVITY_DESCRIPTION, description)
            put(COL_ACTIVITY_HORSE_ID, horseId)
            put(COL_ACTIVITY_USER_ID, userId)
        }
        val result = db.insert(TABLE_ACTIVITY, null, values) != -1L
        db.close()
        return result
    }
}
