package com.arpansircar.notes_app.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arpansircar.notes_app.common.ConstantsBase

@Entity(tableName = ConstantsBase.TABLE_NOTES)
data class Note(
    @PrimaryKey @ColumnInfo(name = ConstantsBase.NOTE_ID) val id: Int? = null,
    @ColumnInfo(name = ConstantsBase.NOTE_TITLE) val noteTitle: String,
    @ColumnInfo(name = ConstantsBase.NOTE_DETAIL) val noteDetail: String,
    @ColumnInfo(name = ConstantsBase.CREATED_AT) val noteCreatedAt: Long
)