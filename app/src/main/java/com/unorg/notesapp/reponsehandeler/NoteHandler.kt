package com.unorg.notesapp.reponsehandeler

sealed class NoteHandler<T>(val data:T?=null,val msg:T?=null){
    data class Success<T>(val getData:T):NoteHandler<T>(data = getData)
    data class Error<T>(val getMsg:T):NoteHandler<T>(msg =getMsg )
}
