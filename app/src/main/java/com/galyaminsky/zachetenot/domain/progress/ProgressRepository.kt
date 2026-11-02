package com.galyaminsky.zachetenot.domain.progress

import kotlinx.coroutines.flow.Flow

interface ProgressRepository {
    val passedTopicIds: Flow<Set<String>>
    suspend fun markPassed(topicId: String)
    suspend fun reset()
}
