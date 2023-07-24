package com.unorg.notesapp.repository

import com.unorg.notesapp.reponsehandeler.NoteHandler
import com.unorg.notesapp.reponsehandeler.ResponseHandler
import com.unorg.notesapp.roomdatabase.DAO
import com.unorg.notesapp.roomdatabase.NoteTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class Repository @Inject constructor(val dao: DAO)
{
     fun insertNotes(noteTable: NoteTable): Flow<NoteHandler<Long>> {
        return ResponseHandler{
            dao.insertNotes(notes =noteTable )
        } as Flow<NoteHandler<Long>>

    }
     fun getNotes(): Flow<NoteHandler<List<NoteTable>>>{
        return ResponseHandler{
            dao.getAllNotes()
        } as Flow<NoteHandler<List<NoteTable>>>

    }
    fun deleteNotes(notes:NoteTable): Flow<NoteHandler<Int>>{
        return ResponseHandler{
            dao.deleteNotes(notes)
        } as Flow<NoteHandler<Int>>

    }
    fun updateNotes(notes:NoteTable): Flow<NoteHandler<Int>>{
        return ResponseHandler{
            dao.updateNotes(notes)
        } as Flow<NoteHandler<Int>>

    }
}



