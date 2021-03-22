package com.sibela.retrofit

data class Todo(
    val id: Int,
    val title: String,
    val completed: Boolean,
    val userId: Int
)