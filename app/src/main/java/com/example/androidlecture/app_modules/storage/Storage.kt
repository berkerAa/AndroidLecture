package com.example.androidlecture.app_modules.storage

interface Storage {
    fun setString(key: String, value: String)
    fun getString(key: String): String

}