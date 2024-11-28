package com.example.myapp014amynotehub.data.model
 //Tímto způsobem můžeme každé poznámce přiřadit více štítků a každý štítek může být připojen k více poznámkám, což vytváří mnoho-na-mnoho (m:n) vztah mezi poznámkami a štítky.

import androidx.room.Entity

@Entity(tableName = "note_tag_cross_ref", primaryKeys = ["noteId", "tagId"])

data class NoteTagCrossRef(
    val noteId: Int,  // ID poznámky
    val tagId: Int    // ID štítku
)