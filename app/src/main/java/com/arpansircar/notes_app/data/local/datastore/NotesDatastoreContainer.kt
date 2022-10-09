package com.arpansircar.notes_app.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.arpansircar.notes_app.common.ConstantsBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesDatastoreContainer(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(ConstantsBase.DATASTORE_NAME)
    private val syncedKey = booleanPreferencesKey(ConstantsBase.KEY_IS_SYNCED)

    // Read from notes datastore
    val syncedKeyFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[syncedKey] ?: false
    }

    // Write to notes datastore
    suspend fun writeToDataStore(isSynced: Boolean) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[syncedKey] = isSynced
        }
    }
}