package com.galyaminsky.zachetenot.domain.content

import com.galyaminsky.zachetenot.domain.model.ContentBundle

interface ContentRepository {
    suspend fun loadContent(): ContentBundle
}
