package com.yorick.curd.data.model

data class User(
    val userId: Int,
    val username: String,
    val email: String,
    val password: String,
    val createdAt: String
)