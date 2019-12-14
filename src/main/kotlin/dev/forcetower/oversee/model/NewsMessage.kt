package dev.forcetower.oversee.model

data class NewsMessage (
    val title: String,
    val link: String,
    val imageUrl: String?,
    val publishDate: String
)