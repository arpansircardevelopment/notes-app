package com.arpansircar.notes_app.domain.repositories

import com.arpansircar.notes_app.data.local.NotesDao
import com.arpansircar.notes_app.di.FirebaseContainer
import com.arpansircar.notes_app.domain.models.Note
import kotlinx.coroutines.flow.Flow

class HomeRepository(
    private val notesDao: NotesDao,
    private val container: FirebaseContainer
) {
    fun fetchNotes(): Flow<List<Note>> {
        return notesDao.loadAllNotes()
    }

    suspend fun addNotes(note: Note): Long {
        return notesDao.insertNote(note)
    }

    suspend fun deleteNote(note: Note) {
        notesDao.deleteNote(note)
    }

    fun fetchNote(noteId: Int): Flow<Note> {
        return notesDao.loadNote(noteId)
    }

    suspend fun updateNote(note: Note): Int {
        return notesDao.updateNote(note)
    }

    fun addOrUpdateNotesOnServer(note: Note) {
        if (note.id != null) note.id = null

        container
            .realtimeDb
            .child("notes")
            .child(container.firebaseAuth.currentUser?.uid!!)
            .child(note.noteUUID)
            .setValue(note)
    }

    fun deleteNoteFromServer(note: Note) {
        container
            .realtimeDb
            .child("notes")
            .child(container.firebaseAuth.currentUser?.uid!!)
            .child(note.noteUUID)
            .removeValue()
    }
}