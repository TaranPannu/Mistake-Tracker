package com.example.mistaketracker.DataClass

data class User(
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String // Consider hashing for security
)