package com.arpansircar.notes_app.domain.repositories

import com.arpansircar.notes_app.data.local.datastore.NotesDatastoreContainer
import com.arpansircar.notes_app.data.local.db.NotesDao
import com.arpansircar.notes_app.di.FirebaseContainer
import com.arpansircar.notes_app.domain.models.Note
import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class HomeRepository(
    private val notesDao: NotesDao,
    private val container: FirebaseContainer,
    private val datastoreContainer: NotesDatastoreContainer
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

    suspend fun readNotesFromServer(): DataSnapshot {
        return container
            .realtimeDb
            .child("notes")
            .child(container.firebaseAuth.currentUser?.uid!!)
            .get()
            .await()
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

    fun isDataSynced(): Flow<Boolean> {
        return datastoreContainer.syncedKeyFlow
    }

    suspend fun setDataSynced(isSynced: Boolean) {
        datastoreContainer.writeToDataStore(isSynced)
    }
}