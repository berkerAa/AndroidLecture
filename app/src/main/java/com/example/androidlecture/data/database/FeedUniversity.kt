package com.example.androidlecture.data.database

import android.provider.BaseColumns

object FeedUniversity {

    object FeedStudent: BaseColumns {
        const val TABLE_NAME = "STUDENT"
        const val COLUMN_NAME_NAME = "Name"
        const val COLUMN_NAME_EMAIL = "Email"
        const val COLUMN_NAME_SURNAME = "Surname"
        const val COLUMN_NAME_BIRTHDATE = "BirthDate"
        const val COLUMN_NAME_PASSWORD = "Password"
        const val COLUMN_NAME_IS_LECTURER = "IsLecturer"
        const val COLUMN_NAME_DEPARTMENT = "Department"
    }



}