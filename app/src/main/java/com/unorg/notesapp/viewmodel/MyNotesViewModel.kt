package com.unorg.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.unorg.notesapp.reponsehandeler.NoteHandler
import com.unorg.notesapp.repository.Repository
import com.unorg.notesapp.roomdatabase.NoteTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MyNotesViewModel@Inject constructor(val repository: Repository):ViewModel() {

    fun updateNotes(noteTable: NoteTable): Flow<NoteHandler<Int>> {
        return repository.updateNotes(noteTable)
    }
}