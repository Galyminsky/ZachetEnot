package com.galyaminsky.zachetenot.domain.catalog

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AppCatalogTest {
    @Test
    fun `grades have unique numbers and only grade three is available`() {
        assertEquals(4, AppCatalog.grades.size)
        assertEquals(setOf(1, 2, 3, 4), AppCatalog.grades.map { it.number }.toSet())
        assertEquals(listOf(3), AppCatalog.grades.filter { it.isAvailable }.map { it.number })
    }

    @Test
    fun `subjects have unique IDs and only russian is available`() {
        assertEquals(4, AppCatalog.subjects.size)
        assertEquals(4, AppCatalog.subjects.map { it.id }.toSet().size)
        assertEquals(listOf("russian"), AppCatalog.subjects.filter { it.isAvailable }.map { it.id })
    }

    @Test
    fun `default grade exists and is available`() {
        val grade = AppCatalog.grades.single { it.number == AppCatalog.DEFAULT_GRADE }
        assertTrue(grade.isAvailable)
    }

    @Test
    fun `default subject exists and is available`() {
        val subject = AppCatalog.subjects.single { it.id == AppCatalog.DEFAULT_SUBJECT_ID }
        assertTrue(subject.isAvailable)
    }
}
