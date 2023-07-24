package com.unorg.notesapp.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [NoteTable::class], version = 1)
abstract class DataBase:RoomDatabase() {
  abstract fun getDao():DAO
}