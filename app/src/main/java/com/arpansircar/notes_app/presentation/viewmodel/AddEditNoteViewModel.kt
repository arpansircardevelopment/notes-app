package com.arpansircar.notes_app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpansircar.notes_app.domain.models.Note
import com.arpansircar.notes_app.domain.repositories.HomeRepository
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class AddEditNoteViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    private val _notesLiveData: MutableLiveData<Long> = MutableLiveData()
    val notesLiveData: LiveData<Long> = _notesLiveData

    private val _fetchNoteLiveData: MutableLiveData<Note> = MutableLiveData()
    val fetchNotesLiveData: LiveData<Note> = _fetchNoteLiveData

    private val _updateNoteLiveData: MutableLiveData<Int> = MutableLiveData()
    val updateNoteLiveData: LiveData<Int> = _updateNoteLiveData

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            val position: Long = withContext(Dispatchers.IO) {
                homeRepository.addNotes(note)
            }
            withContext(Dispatchers.IO) {
                homeRepository.addOrUpdateNotesOnServer(note)
            }
            _notesLiveData.postValue(position)
        }
    }

    fun validateData(
        noteTitleEditText: TextInputEditText?,
        noteDetailEditText: TextInputEditText?
    ): Boolean {
        if (noteTitleEditText?.text?.isEmpty() == true) {
            noteTitleEditText.error = "Field cannot be empty"
            return false
        }

        if (noteDetailEditText?.text?.isEmpty() == true) {
            noteDetailEditText.error = "Field cannot be empty"
            return false
        }
        return true
    }

    fun getNote(noteID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            homeRepository.fetchNote(noteID).collectLatest {
                _fetchNoteLiveData.postValue(it)
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            val position: Int = withContext(Dispatchers.IO) {
                homeRepository.updateNote(note)
            }
            withContext(Dispatchers.IO) {
                homeRepository.addOrUpdateNotesOnServer(note)
            }
            _updateNoteLiveData.postValue(position)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            homeRepository.deleteNote(note)
        }
    }
}