package com.example.androidlecture.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


class FeedStudentHelper(context: Context):  SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "FeedUniversity.db"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${FeedUniversity.FeedStudent.TABLE_NAME} (" +
                    "${BaseColumns._ID} INT PRIMARY KEY," +
                    "${FeedUniversity.FeedStudent.COLUMN_NAME_NAME}  VARCHAR(255)," +
                    "${FeedUniversity.FeedStudent.COLUMN_NAME_EMAIL}  VARCHAR(255)," +
                    "${FeedUniversity.FeedStudent.COLUMN_NAME_SURNAME} VARCHAR(255)," +
                    "${FeedUniversity.FeedStudent.COLUMN_NAME_BIRTHDATE} VARCHAR(255)," +
                    "${FeedUniversity.FeedStudent.COLUMN_NAME_PASSWORD} VARCHAR(255)," +
                    "${FeedUniversity.FeedStudent.COLUMN_NAME_IS_LECTURER} TINYINT," +
                    "${FeedUniversity.FeedStudent.COLUMN_NAME_DEPARTMENT} VARCHAR(255))"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedUniversity.FeedStudent.TABLE_NAME}"

    }


}
