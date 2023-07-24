package com.unorg.notesapp.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("MyTable")
data class NoteTable(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val title: String? = null,
    @ColumnInfo val mainNote: String? = null,
    @ColumnInfo  val color: String? = null
)
