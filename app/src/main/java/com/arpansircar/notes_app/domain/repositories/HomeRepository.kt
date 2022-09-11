package com.arpansircar.notes_app.domain.repositories

import com.arpansircar.notes_app.data.local.NotesDao
import com.arpansircar.notes_app.domain.models.Note
import kotlinx.coroutines.flow.Flow

class HomeRepository(private val notesDao: NotesDao) {
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
}