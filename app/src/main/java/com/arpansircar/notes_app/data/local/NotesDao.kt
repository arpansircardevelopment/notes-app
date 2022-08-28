package com.arpansircar.notes_app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.arpansircar.notes_app.common.ConstantsBase
import com.arpansircar.notes_app.domain.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert
    suspend fun insertNote(note: Note): Long

    @Query("SELECT * FROM ${ConstantsBase.TABLE_NOTES} ORDER BY ${ConstantsBase.CREATED_AT} DESC")
    fun loadAllNotes(): Flow<List<Note>>

    @Delete
    suspend fun deleteNote(note: Note)

}