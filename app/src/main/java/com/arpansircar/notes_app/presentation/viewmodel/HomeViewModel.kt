package com.arpansircar.notes_app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpansircar.notes_app.domain.models.Note
import com.arpansircar.notes_app.domain.repositories.HomeRepository
import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _notesLiveData: MutableLiveData<List<Note>> = MutableLiveData()
    val notesLiveData: LiveData<List<Note>> = _notesLiveData

    private val _syncedLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val syncedLiveData: LiveData<Boolean> = _syncedLiveData

    fun isSynced() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.isDataSynced().collectLatest {
                _syncedLiveData.postValue(it)
            }
        }
    }

    fun setSynced(isDataSynced: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setDataSynced(isDataSynced)
        }
    }

    fun downloadNotes() {
        val notesList: MutableList<Note?> = mutableListOf()

        viewModelScope.launch(Dispatchers.IO) {
            val snapshot: DataSnapshot = repository.readNotesFromServer()
            for (abc: DataSnapshot in snapshot.children) {
                notesList.add(
                    Note(
                        noteUUID = abc.child("noteUUID").value.toString(),
                        noteTitle = abc.child("noteTitle").value.toString(),
                        noteDetail = abc.child("noteDetail").value.toString(),
                        noteCreatedAt = abc.child("noteCreatedAt").value.toString().toLong()
                    )
                )
            }
        }
    }

    fun fetchNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchNotes().collect {
                _notesLiveData.postValue(it)
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                repository.deleteNote(note)
            }
            withContext(Dispatchers.IO) {
                repository.deleteNoteFromServer(note)
            }
        }
    }
}