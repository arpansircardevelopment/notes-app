package com.arpansircar.notes_app.common.utils

import com.arpansircar.notes_app.domain.models.Note
import com.google.firebase.database.DataSnapshot

object FirebaseUtils {

    fun convertSnapshotToObject(dataSnapshot: DataSnapshot): List<Note> {
        val notesList: MutableList<Note> = mutableListOf()
        for (snapshot: DataSnapshot in dataSnapshot.children) {
            notesList.add(convertSnapshotToNote(snapshot))
        }
        return notesList
    }

    private fun convertSnapshotToNote(snapshot: DataSnapshot): Note {
        return Note(
            noteUUID = snapshot.child("noteUUID").value.toString(),
            noteTitle = snapshot.child("noteTitle").value.toString(),
            noteDetail = snapshot.child("noteDetail").value.toString(),
            noteCreatedAt = snapshot.child("noteCreatedAt").value.toString().toLong()
        )
    }
}