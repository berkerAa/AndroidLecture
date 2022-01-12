package com.example.androidlecture.src.user.data

import java.util.*

data class RegisterModel (
    val name: String,
    val surname: String,
    val birthDate: String,
    val password: String,
    val department: String,
    val isLecturer: Boolean,
    val email: String
        )