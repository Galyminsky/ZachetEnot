package com.galyaminsky.zachetenot.data

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.After
import org.junit.Test
import org.junit.rules.TemporaryFolder

class DataStoreProgressRepositoryTest {
    private val dataStoreJob = SupervisorJob()
    private val dataStoreScope = CoroutineScope(dataStoreJob + Dispatchers.IO)

    @get:Rule
    val temporaryFolder = TemporaryFolder()

    @After
    fun closeDataStore() = runBlocking {
        dataStoreJob.cancelAndJoin()
    }

    @Test
    fun `passed topic IDs are initially empty`() = runTest {
        val repository = createRepository(dataStoreScope)

        assertEquals(emptySet<String>(), repository.passedTopicIds.first())
    }

    @Test
    fun `markPassed adds a topic ID`() = runTest {
        val repository = createRepository(dataStoreScope)

        repository.markPassed("topic-1")

        assertEquals(setOf("topic-1"), repository.passedTopicIds.first())
    }

    @Test
    fun `markPassed is idempotent for the same topic ID`() = runTest {
        val repository = createRepository(dataStoreScope)

        repository.markPassed("topic-1")
        repository.markPassed("topic-1")

        assertEquals(setOf("topic-1"), repository.passedTopicIds.first())
    }

    @Test
    fun `markPassed keeps two different topic IDs`() = runTest {
        val repository = createRepository(dataStoreScope)

        repository.markPassed("topic-1")
        repository.markPassed("topic-2")

        assertEquals(setOf("topic-1", "topic-2"), repository.passedTopicIds.first())
    }

    @Test
    fun `reset removes all passed topic IDs`() = runTest {
        val repository = createRepository(dataStoreScope)
        repository.markPassed("topic-1")
        repository.markPassed("topic-2")

        repository.reset()

        assertEquals(emptySet<String>(), repository.passedTopicIds.first())
    }

    private fun createRepository(scope: CoroutineScope): DataStoreProgressRepository {
        val dataStore = PreferenceDataStoreFactory.create(
            scope = scope,
            produceFile = { temporaryFolder.root.resolve("progress.preferences_pb") }
        )
        return DataStoreProgressRepository(dataStore)
    }
}
