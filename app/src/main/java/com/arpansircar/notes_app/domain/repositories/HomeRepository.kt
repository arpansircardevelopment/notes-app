package com.arpansircar.notes_app.domain.repositories

import com.arpansircar.notes_app.common.utils.FirebaseUtils
import com.arpansircar.notes_app.data.local.datastore.NotesDatastoreContainer
import com.arpansircar.notes_app.data.local.db.NotesDao
import com.arpansircar.notes_app.data.network.AuthInvoker
import com.arpansircar.notes_app.di.container.FirebaseContainerRoot
import com.arpansircar.notes_app.domain.models.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class HomeRepository(
    private val notesDao: NotesDao,
    private val container: FirebaseContainerRoot,
    private val datastoreContainer: NotesDatastoreContainer,
    private val authInvoker: AuthInvoker,
    private val firebaseAuth: FirebaseAuth
) {
    fun fetchNotes(): Flow<List<Note>> {
        return notesDao.loadAllNotes()
    }

    suspend fun addNotes(note: Note): Long {
        return notesDao.insertNote(note)
    }

    private suspend fun insertNotes(notesList: List<Note>) {
        notesDao.insertNotes(notesList)
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

    suspend fun readNotesFromServer() {
        val snapshot =
            container.realtimeDb.child("notes").child(container.firebaseAuth.currentUser?.uid!!)
                .get().await()

        insertNotes(FirebaseUtils.convertSnapshotToObject(snapshot))
        datastoreContainer.writeToDataStore(true)
    }

    fun addOrUpdateNotesOnServer(note: Note) {
        if (note.id != null) note.id = null

        container.realtimeDb.child("notes").child(container.firebaseAuth.currentUser?.uid!!)
            .child(note.noteUUID).setValue(note)
    }

    fun deleteNoteFromServer(note: Note) {
        container.realtimeDb.child("notes").child(container.firebaseAuth.currentUser?.uid!!)
            .child(note.noteUUID).removeValue()
    }

    fun isDataSynced(): Flow<Boolean> {
        return datastoreContainer.syncedKeyFlow
    }

    fun getCurrentUserDetails(): FirebaseUser? = container.currentUser

    private fun getFirebaseAuth(): FirebaseAuth = container.firebaseAuth

    fun userSignOut(): Unit = getFirebaseAuth().signOut()

    suspend fun updateUserProfile(request: UserProfileChangeRequest): String? {
        return authInvoker.invoke {
            firebaseAuth.currentUser?.updateProfile(request)?.await()
        }
    }
}