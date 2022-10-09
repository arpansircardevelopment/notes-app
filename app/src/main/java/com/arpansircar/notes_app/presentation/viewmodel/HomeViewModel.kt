package com.arpansircar.notes_app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpansircar.notes_app.common.utils.FirebaseUtils
import com.arpansircar.notes_app.domain.models.Note
import com.arpansircar.notes_app.domain.repositories.HomeRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.ktx.Firebase
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

    fun downloadNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.readNotesFromServer()
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