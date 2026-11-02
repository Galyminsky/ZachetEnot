package com.galyaminsky.zachetenot.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class ContentParserTest {
    @Test
    fun `parses a small JSON literal`() {
        val json = """
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

        val result = ContentParser.parse(json)

        assertEquals(1, result.topics.size)
        assertEquals(1, result.rules.size)
        assertEquals(1, result.questions.size)
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
}
