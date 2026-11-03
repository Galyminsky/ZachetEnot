package com.galyaminsky.zachetenot.data

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.PreferencesSerializer
import androidx.datastore.core.okio.OkioStorage
import com.galyaminsky.zachetenot.domain.model.Topic
import com.galyaminsky.zachetenot.domain.progress.ProgressCalculator
import com.galyaminsky.zachetenot.domain.progress.TopicStatus
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
import okio.FileSystem
import okio.Path.Companion.toPath

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

    @Test
    fun `reset locks later topics and makes the first available again`() = runTest {
        val repository = createRepository(dataStoreScope)
        val topics = listOf(
            Topic(id = "topic-1", title = "First", order = 1),
            Topic(id = "topic-2", title = "Second", order = 2),
            Topic(id = "topic-3", title = "Third", order = 3)
        )
        repository.markPassed("topic-1")
        repository.markPassed("topic-2")

        repository.reset()

        val progress = ProgressCalculator.calculate(topics, repository.passedTopicIds.first())
        assertEquals(
            listOf(TopicStatus.AVAILABLE, TopicStatus.LOCKED, TopicStatus.LOCKED),
            progress.topics.map { it.status }
        )
    }

    @Test
    fun `new repository reads IDs from the same DataStore`() = runTest {
        val dataStore = createDataStore(dataStoreScope)
        DataStoreProgressRepository(dataStore).markPassed("topic-1")

        val recreatedRepository = DataStoreProgressRepository(dataStore)

        assertEquals(setOf("topic-1"), recreatedRepository.passedTopicIds.first())
    }

    @Test
    fun `markPassed saves a new ID after reset`() = runTest {
        val repository = createRepository(dataStoreScope)
        repository.markPassed("topic-1")
        repository.reset()

        repository.markPassed("topic-2")

        assertEquals(setOf("topic-2"), repository.passedTopicIds.first())
    }

    private fun createRepository(scope: CoroutineScope): DataStoreProgressRepository {
        return DataStoreProgressRepository(createDataStore(scope))
    }

    private fun createDataStore(scope: CoroutineScope) =
        PreferenceDataStoreFactory.create(
            scope = scope,
            storage = OkioStorage(
                fileSystem = FileSystem.SYSTEM,
                serializer = PreferencesSerializer,
                producePath = { temporaryFolder.root.resolve("progress.preferences_pb").absolutePath.toPath() }
            )
        )
}
