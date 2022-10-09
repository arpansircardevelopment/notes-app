package com.arpansircar.notes_app.data.local.db

import androidx.room.*
import com.arpansircar.notes_app.common.ConstantsBase
import com.arpansircar.notes_app.domain.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert
    suspend fun insertNote(note: Note): Long

    @Insert
    suspend fun insertNotes(notesList: List<Note>)

    @Query("SELECT * FROM ${ConstantsBase.TABLE_NOTES} ORDER BY ${ConstantsBase.CREATED_AT} DESC")
    fun loadAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM ${ConstantsBase.TABLE_NOTES} WHERE ${ConstantsBase.NOTE_ID}=:noteID")
    fun loadNote(noteID: Int): Flow<Note>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: Note): Int

    @Delete
    suspend fun deleteNote(note: Note)

}