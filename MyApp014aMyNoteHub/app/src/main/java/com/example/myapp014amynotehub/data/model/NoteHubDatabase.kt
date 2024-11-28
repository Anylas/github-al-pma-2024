package com.example.myapp014amynotehub.data.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapp014amynotehub.data.dao.CategoryDao
import com.example.myapp014amynotehub.data.dao.NoteDao
import com.example.myapp014amynotehub.data.dao.NoteTagDao
import com.example.myapp014amynotehub.data.dao.TagDao

@Database(
    entities = [Note::class, Category::class, Tag::class, NoteTagCrossRef::class],
    version = 1, //číslo verze, bude se pak zvyšovat časem nejspíše
    exportSchema = false
)

abstract class NoteHubDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun categoryDao(): CategoryDao
    abstract fun tagDao(): TagDao
    abstract fun noteTagDao(): NoteTagDao
}