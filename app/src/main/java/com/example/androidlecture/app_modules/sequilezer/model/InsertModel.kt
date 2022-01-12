package com.example.androidlecture.app_modules.sequilezer.model

data class InsertModel @JvmOverloads constructor(
    val table: DatabaseSchemas,
    val values: ArrayList<ArrayList<Any>>,
)