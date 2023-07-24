package com.unorg.notesapp.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DAO
{
    @Insert
    suspend fun insertNotes(notes: NoteTable):Long

    @Query("Select * from MyTable")
    suspend fun getAllNotes():List<NoteTable>

    @Delete
    suspend fun deleteNotes(notes: NoteTable):Int

    @Update
    suspend fun updateNotes(notes: NoteTable):Int
}