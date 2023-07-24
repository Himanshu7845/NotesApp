package com.unorg.notesapp.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.unorg.notesapp.reponsehandeler.NoteHandler
import com.unorg.notesapp.repository.Repository
import com.unorg.notesapp.roomdatabase.NoteTable
import com.unorg.notesapp.views.NotesActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    val application: Application,
    val repository: Repository
) : ViewModel() {

    val result = repository.getNotes()

    fun insertNotes(noteTable: NoteTable): Flow<NoteHandler<Long>> {
        return repository.insertNotes(noteTable)
    }
    fun deleteNotes(noteTable: NoteTable): Flow<NoteHandler<Int>> {
        return repository.deleteNotes(noteTable)
    }

    fun goToNextPage() {
        val intent = Intent(application, NotesActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        application.startActivity(intent)
    }

}