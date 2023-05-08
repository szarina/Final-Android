package com.example.finalandroid.data_classes

data class Comment (
    val id: Int,
    val user: User,
    val content: String,

    val film :Film
)

