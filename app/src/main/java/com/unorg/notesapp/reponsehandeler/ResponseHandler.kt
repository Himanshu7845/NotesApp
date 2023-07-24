package com.unorg.notesapp.reponsehandeler

import kotlinx.coroutines.flow.flow

fun<T> ResponseHandler(call:suspend ()->T)= flow<NoteHandler<Any>> {
    try{
        val result = call.invoke()
        when(result){
            is List<*> ->{
                emit(NoteHandler.Success(result))
            }
            is Int->{
                emit(NoteHandler.Success(result))
            }
            is Long ->{
                emit(NoteHandler.Success(result))
            }

        }
    }
    catch (e:Exception){
        emit(NoteHandler.Error(e.message.toString()))
    }
}