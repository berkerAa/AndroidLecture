package com.example.androidlecture.app_modules.storage

import android.content.Context
import com.google.gson.Gson
import javax.inject.Inject

class SharedPreferencesStorage @Inject constructor(context: Context) : Storage {

    private val sharedPreferences = context.getSharedPreferences("I-TOYS", Context.MODE_PRIVATE)

    override fun setString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    override fun getString(key: String): String {
        return sharedPreferences.getString(key, "")!!
    }

    override fun <T> getModel(key: String, modelSkeleton: Class<T>): T {
        val gson = Gson()
        val json = sharedPreferences.getString(key, null)
        return gson.fromJson(json, modelSkeleton)
    }

    override fun <T> setModel(key: String, model: T) {
        val gson = Gson()
        with(sharedPreferences.edit()){
            putString(key, gson.toJson(model))
            apply()
        }
    }
}