package com.example.finalandroid.data_classes

data class Rating(
    val id: Int,
    val user: User,
    val rating: Float,

    val film :Film
)
