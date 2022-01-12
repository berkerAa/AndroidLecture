package com.example.androidlecture.app_modules.sequilezer.model

sealed class DatabaseSchemas


data class StudentModel @JvmOverloads constructor(
    val name: String? = null,
    val surname: String? = null,
    val email: String? = null,
    val password: String? = null,
    val birthDate: String? = null,
    val id: Int? = null,
    val department: String? = null,
    val isLecturer: Int? = null,

) : DatabaseSchemas()
