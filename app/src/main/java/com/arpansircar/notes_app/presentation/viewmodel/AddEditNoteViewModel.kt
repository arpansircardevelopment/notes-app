package com.arpansircar.notes_app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpansircar.notes_app.domain.models.Note
import com.arpansircar.notes_app.domain.repositories.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddEditNoteViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    private val _notesLiveData: MutableLiveData<Long> = MutableLiveData()
    val notesLiveData: LiveData<Long> = _notesLiveData

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            val position: Long = homeRepository.addNotes(note)
            _notesLiveData.postValue(position)
        }
    }
}