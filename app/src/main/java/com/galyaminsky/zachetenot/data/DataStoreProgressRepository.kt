package com.galyaminsky.zachetenot.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.galyaminsky.zachetenot.domain.progress.ProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreProgressRepository(
    private val dataStore: DataStore<Preferences>
) : ProgressRepository {
    private val passedTopicIdsKey = stringSetPreferencesKey("passed_topic_ids")

    override val passedTopicIds: Flow<Set<String>> = dataStore.data.map { preferences ->
        preferences[passedTopicIdsKey] ?: emptySet()
    }

    override suspend fun markPassed(topicId: String) {
        dataStore.edit { preferences ->
            val currentIds = preferences[passedTopicIdsKey] ?: emptySet()
            preferences[passedTopicIdsKey] = currentIds + topicId
        }
    }

    override suspend fun reset() {
        dataStore.edit { preferences ->
            preferences.remove(passedTopicIdsKey)
        }
    }
}
