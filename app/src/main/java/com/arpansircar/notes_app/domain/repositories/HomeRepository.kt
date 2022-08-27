package com.arpansircar.notes_app.domain.repositories

import com.arpansircar.notes_app.data.local.NotesDao
import com.arpansircar.notes_app.data.network.AuthInvoker
import com.arpansircar.notes_app.domain.models.Note
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow

class HomeRepository(
    private val firebaseAuth: FirebaseAuth,
    private val authInvoker: AuthInvoker,
    private val notesDao: NotesDao
) {
    fun fetchNotes(): Flow<List<Note>> {
        return notesDao.loadAllNotes()
    }

    suspend fun addNotes(note: Note) {
        notesDao.insertNote(note)
    }
}