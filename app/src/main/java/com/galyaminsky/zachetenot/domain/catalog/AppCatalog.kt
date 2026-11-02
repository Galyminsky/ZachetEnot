package com.galyaminsky.zachetenot.domain.catalog

object AppCatalog {
    val grades = listOf(Grade(1, false), Grade(2, false), Grade(3, true), Grade(4, false))
    val subjects = listOf(Subject("russian", true), Subject("math", false), Subject("literature", false), Subject("world", false))
    const val DEFAULT_GRADE = 3
    const val DEFAULT_SUBJECT_ID = "russian"
}
