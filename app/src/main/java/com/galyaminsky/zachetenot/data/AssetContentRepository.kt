package com.galyaminsky.zachetenot.data

import android.content.Context
import com.galyaminsky.zachetenot.domain.content.ContentRepository
import com.galyaminsky.zachetenot.domain.model.ContentBundle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AssetContentRepository(private val context: Context) : ContentRepository {
    override suspend fun loadContent(): ContentBundle = withContext(Dispatchers.IO) {
        val content = context.assets.open(CONTENT_FILE)
            .bufferedReader(Charsets.UTF_8)
            .use { it.readText() }
        ContentParser.parse(content)
    }

    private companion object {
        const val CONTENT_FILE = "content/russian_grade3.json"
    }
}
