package com.galyaminsky.zachetenot.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
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
    fun `parses the temporary grade three content`() {
        val file = File("src/main/assets/content/russian_grade3.json")
        val result = ContentParser.parse(file.readText())

        assertEquals(1, result.topics.size)
        assertEquals(3, result.rules.size)
        assertEquals(24, result.questions.size)
        result.rules.forEach { rule ->
            assertEquals(true, result.questions.count { it.ruleId == rule.id } >= 8)
        }
    }
}
