package com.galyaminsky.zachetenot.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import com.galyaminsky.zachetenot.domain.model.TestConfig
import com.galyaminsky.zachetenot.domain.test.TestEngine
import kotlin.random.Random

class ContentParserTest {
    private val validJson = """
            {
              "topics": [{"id":"t1","title":"Тема","order":1}],
              "rules": [{"id":"r1","title":"Правило","explanation":"Объяснение."}],
              "questions": [{
                "id":"q1","topicId":"t1","ruleId":"r1","text":"Вопрос?",
                "options":[{"id":"a","text":"А"},{"id":"b","text":"Б"}],
                "correctOptionId":"a"
              }]
            }
        """.trimIndent()
    private val questionJson = validJson.substringAfter("\"questions\": [").substringBeforeLast("]")

    @Test
    fun `parses a small JSON literal`() {
        val result = ContentParser.parse(validJson)

        assertEquals(1, result.topics.size)
        assertEquals(1, result.rules.size)
        assertEquals(1, result.questions.size)
    }

    @Test
    fun `rejects damaged JSON`() {
        assertThrows(Exception::class.java) { ContentParser.parse(validJson.dropLast(1)) }
    }

    @Test
    fun `empty question bank remains empty for no content handling`() {
        val result = ContentParser.parse(validJson.replace("\"questions\": [$questionJson]", "\"questions\": []"))

        assertTrue(result.questions.isEmpty())
    }

    @Test
    fun `rejects duplicate topic rule and question IDs`() {
        val topic = "{\"id\":\"t1\",\"title\":\"Тема\",\"order\":1}"
        val rule = "{\"id\":\"r1\",\"title\":\"Правило\",\"explanation\":\"Объяснение.\"}"
        val variants = listOf(
            validJson.replace(topic, "$topic,$topic"),
            validJson.replace(rule, "$rule,$rule"),
            validJson.replace("\"questions\": [$questionJson]", "\"questions\": [$questionJson,$questionJson]")
        )

        variants.forEach { json ->
            assertThrows(IllegalArgumentException::class.java) { ContentParser.parse(json) }
        }
    }

    @Test
    fun `rejects unknown topic and correct option IDs`() {
        val variants = listOf(
            validJson.replace("\"topicId\":\"t1\"", "\"topicId\":\"missing\""),
            validJson.replace("\"correctOptionId\":\"a\"", "\"correctOptionId\":\"missing\"")
        )

        variants.forEach { json ->
            assertThrows(IllegalArgumentException::class.java) { ContentParser.parse(json) }
        }
    }

    @Test
    fun `question with unknown rule ID throws`() {
        val json = """
            {
              "topics": [{"id":"t1","title":"Тема","order":1}],
              "rules": [],
              "questions": [{
                "id":"q1","topicId":"t1","ruleId":"missing","text":"Вопрос?",
                "options":[{"id":"a","text":"А"},{"id":"b","text":"Б"}],
                "correctOptionId":"a"
              }]
            }
        """.trimIndent()

        assertThrows(IllegalArgumentException::class.java) { ContentParser.parse(json) }
    }

    @Test
    fun `parses the current grade three content`() {
        val file = File("src/main/assets/content/russian_grade3.json")
        val result = ContentParser.parse(file.readText())

        assertEquals(6, result.topics.size)
        assertEquals(12, result.rules.size)
        assertEquals(138, result.questions.size)

        assertEquals(result.topics.size, result.topics.map { it.id }.distinct().size)
        assertEquals(result.rules.size, result.rules.map { it.id }.distinct().size)
        assertEquals(result.questions.size, result.questions.map { it.id }.distinct().size)

        val topicIds = result.topics.map { it.id }.toSet()
        val ruleIds = result.rules.map { it.id }.toSet()
        result.questions.forEach { question ->
            assertTrue(question.topicId in topicIds)
            assertTrue(question.ruleId in ruleIds)
            assertEquals(
                question.options.size,
                question.options.map { it.id }.distinct().size
            )
            assertEquals(
                1,
                question.options.count { it.id == question.correctOptionId }
            )
        }

        val expectedQuestionsPerTopic = mapOf(
            "topic_speech_text" to 26,
            "topic_sentence" to 20,
            "topic_word_structure" to 28,
            "topic_root_spelling" to 24,
            "topic_noun" to 20,
            "topic_adjective" to 20
        )
        assertEquals(
            expectedQuestionsPerTopic,
            result.topics.associate { topic ->
                topic.id to result.questions.count { it.topicId == topic.id }
            }
        )

        result.topics.forEach { topic ->
            assertEquals(true, topic.description.isNotEmpty())
        }
    }

    @Test
    fun `working asset matches export and supports every topic test`() {
        val asset = File("src/main/assets/content/russian_grade3.json")
        val export = File("../content/android/russian_grade3.json")
        assertArrayEquals(export.readBytes(), asset.readBytes())

        val content = ContentParser.parse(asset.readText())
        content.topics.forEach { topic ->
            val bank = content.questionsOf(topic.id)
            assertTrue("${topic.id} has fewer than 15 questions", bank.size >= TestConfig.QUESTIONS_PER_TEST)
            val session = TestEngine(Random(42)).startSession(bank)
            assertEquals(TestConfig.QUESTIONS_PER_TEST, session.questions.size)
            assertEquals(TestConfig.QUESTIONS_PER_TEST, session.questions.map { it.id }.toSet().size)
            assertTrue(session.questions.all { it.topicId == topic.id })
        }
    }
}
