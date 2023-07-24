package com.unorg.notesapp.roomdatabase

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Module
{
    @Provides
    fun getDataBase(@ApplicationContext app: Context):DataBase{

        return  Room.databaseBuilder(
            app,
            DataBase::class.java, "NotesDataBase"
        ).build()
    }

    @Provides
    fun getDao(dataBase: DataBase):DAO{
       return dataBase.getDao()
    }
}