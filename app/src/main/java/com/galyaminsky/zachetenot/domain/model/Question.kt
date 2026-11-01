package com.galyaminsky.zachetenot.domain.model

data class Question(
    val id: String,
    val topicId: String,
    val ruleId: String,
    val text: String,
    val options: List<Option>,
    val correctOptionId: String
) {
    init {
        require(options.size >= 2) { "A question must have at least two options" }
        require(options.map { it.id }.toSet().size == options.size) { "Option IDs must be unique" }
        require(options.any { it.id == correctOptionId }) { "The correct option ID must match an option" }
        require(text.isNotBlank()) { "Question text must not be empty" }
    }
}
