package com.itlab.notes.media

import com.itlab.domain.model.ContentItem
import com.itlab.domain.model.DataSource
import java.io.File

fun List<ContentItem>.withoutTextItems(): List<ContentItem> = filterNot { it is ContentItem.Text }

fun List<ContentItem>.imageAttachments(): List<ContentItem.Image> = filterIsInstance<ContentItem.Image>()

fun DataSource.toCoilModel(): Any? {
    localPath
        ?.takeIf { it.isNotBlank() }
        ?.let { path ->
            val file = File(path)
            if (file.isFile && file.canRead() && file.length() > 0L) {
                return file
            }
        }
    return remoteUrl?.takeIf { it.isNotBlank() }
}
