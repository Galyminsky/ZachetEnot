package com.galyaminsky.zachetenot.domain.model

data class ContentBundle(
    val topics: List<Topic>,
    val rules: List<Rule>,
    val questions: List<Question>
) {
    fun questionsOf(topicId: String): List<Question> = questions.filter { it.topicId == topicId }

    fun ruleOf(ruleId: String): Rule = rules.first { it.id == ruleId }
}
