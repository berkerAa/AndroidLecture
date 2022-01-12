package com.example.androidlecture.app_modules.sequilezer.model

data class SelectModel  @JvmOverloads constructor(
    val columns: Array<String>? = null,
    val selectionKey: String? = null,
    val selectionValue: Array<String>? = null,
    val sortKey: String? = null,
    val sortType: String? = null,
    val table: DatabaseSchemas
)

