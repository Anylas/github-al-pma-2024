package com.example.myapp014amynotehub.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.myapp014amynotehub.data.model.Note
import com.example.myapp014amynotehub.data.model.Tag
import com.example.myapp014amynotehub.data.model.NoteTagCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteTagDao {

    // Vloží vazbu mezi poznámkou a štítkem
    @Insert
    suspend fun insert(noteTagCrossRef: NoteTagCrossRef)

    // Načte všechny štítky přidružené k určité poznámce
    @Transaction
    @Query("SELECT * FROM tag_table INNER JOIN note_tag_cross_ref ON tag_table.id = note_tag_cross_ref.tagId WHERE note_tag_cross_ref.noteId = :noteId")
    fun getTagsForNote(noteId: Int): Flow<List<Tag>>

    // Načte všechny poznámky přidružené k určitému štítku
    @Transaction
    @Query("SELECT * FROM note_table INNER JOIN note_tag_cross_ref ON note_table.id = note_tag_cross_ref.noteId WHERE note_tag_cross_ref.tagId = :tagId")
    fun getNotesForTag(tagId: Int): Flow<List<Note>>

    // Vymaže všechny záznamy z tabulky
    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()
}