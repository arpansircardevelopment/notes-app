package com.arpansircar.notes_app.data.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arpansircar.notes_app.common.ConstantsBase
import com.arpansircar.notes_app.domain.models.Note

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao

    companion object {
        private var INSTANCE: NotesDatabase? = null

        fun getInstance(context: Application): NotesDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    NotesDatabase::class.java,
                    ConstantsBase.NOTES_DATABASE
                ).build()
            }

            return INSTANCE!!;
        }
    }
}