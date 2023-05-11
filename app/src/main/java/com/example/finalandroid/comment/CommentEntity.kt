package com.example.finalandroid.comment

import com.example.finalandroid.data_classes.Film
import com.example.finalandroid.data_classes.User

data class CommentEntity(
    val id: Int,
    val user: User,
    val content: String,

    val film : Film
)