package com.galyaminsky.zachetenot.data

import com.galyaminsky.zachetenot.domain.model.Option
import com.galyaminsky.zachetenot.domain.model.ContentBundle
import com.galyaminsky.zachetenot.domain.model.Question
import com.galyaminsky.zachetenot.domain.model.Rule
import com.galyaminsky.zachetenot.domain.model.Topic
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object ContentParser {
    private val jsonFormat = Json

    fun parse(json: String): ContentBundle {
        val dto = jsonFormat.decodeFromString<ContentBundleDto>(json)
        val topics = dto.topics.map { Topic(it.id, it.title, it.order, description = it.description) }
        val rules = dto.rules.map { Rule(it.id, it.title, it.explanation) }
        val questions = dto.questions.map { question ->
            Question(
                id = question.id,
                topicId = question.topicId,
                ruleId = question.ruleId,
                text = question.text,
                options = question.options.map { Option(it.id, it.text) },
                correctOptionId = question.correctOptionId
            )
        }

        val topicIds = topics.map { it.id }.toSet()
        val ruleIds = rules.map { it.id }.toSet()
        require(questions.map { it.id }.toSet().size == questions.size) {
            "Question IDs must be unique"
        }
        questions.forEach { question ->
            require(question.topicId in topicIds) { "Unknown topic ID: ${question.topicId}" }
            require(question.ruleId in ruleIds) { "Unknown rule ID: ${question.ruleId}" }
        }

        return ContentBundle(topics, rules, questions)
    }

    @Serializable
    private data class ContentBundleDto(
        val topics: List<TopicDto>,
        val rules: List<RuleDto>,
        val questions: List<QuestionDto>
    )

    @Serializable
    private data class TopicDto(val id: String, val title: String, val order: Int, val description: String = "")

    @Serializable
    private data class RuleDto(val id: String, val title: String, val explanation: String)

    @Serializable
    private data class OptionDto(val id: String, val text: String)

    @Serializable
    private data class QuestionDto(
        val id: String,
        val topicId: String,
        val ruleId: String,
        val text: String,
        val options: List<OptionDto>,
        val correctOptionId: String
    )
}
