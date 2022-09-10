package com.arpansircar.notes_app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpansircar.notes_app.domain.models.Note
import com.arpansircar.notes_app.domain.repositories.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _notesLiveData: MutableLiveData<List<Note>> = MutableLiveData()
    val notesLiveData: LiveData<List<Note>> = _notesLiveData

    fun fetchNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchNotes().collect {
                _notesLiveData.postValue(it)
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }
}