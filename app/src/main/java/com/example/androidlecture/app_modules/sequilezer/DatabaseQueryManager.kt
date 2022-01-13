package com.example.androidlecture.app_modules.sequilezer

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import com.example.androidlecture.app_modules.sequilezer.model.*
import com.example.androidlecture.data.database.FeedUniversity
import java.lang.Exception
import java.lang.reflect.Array
import kotlin.reflect.typeOf

class DatabaseQueryManager constructor(private val db: SQLiteOpenHelper) {

    private val readable = db.readableDatabase

    private val writeable = db.writableDatabase

    fun select(selectModel: SelectModel): ArrayList<DatabaseSchemas> {
           return when (selectModel.table) {
                is StudentModel -> selectStudent(selectModel)
               else -> selectEverything()
            }


        //return selectEverything()
    }
    fun delete(selectModel: SelectModel): Boolean {
        return deleteStudent(selectModel)
    }

    fun insert(insertModel: InsertModel): Boolean {
        return when (insertModel.table) {
            is StudentModel -> insertStudent(insertModel)
            else -> false
        }
    }
    private fun selectEverything(): ArrayList<DatabaseSchemas>{
        val cursor = readable.query(
            FeedUniversity.FeedStudent.TABLE_NAME,   // The table to query
            null,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )
        val students = ArrayList<DatabaseSchemas>()
        with(cursor)
        {
            while (moveToNext())
            {
                students.add(
                    StudentModel(
                        id = getInt(getColumnIndexOrThrow(BaseColumns._ID)),
                        name = getString(getColumnIndexOrThrow(FeedUniversity.FeedStudent.COLUMN_NAME_NAME)),
                        surname = getString(getColumnIndexOrThrow(FeedUniversity.FeedStudent.COLUMN_NAME_SURNAME)),
                        password = getString(getColumnIndexOrThrow(FeedUniversity.FeedStudent.COLUMN_NAME_PASSWORD)),
                        isLecturer = getInt(getColumnIndexOrThrow(FeedUniversity.FeedStudent.COLUMN_NAME_IS_LECTURER)),
                        birthDate = getString(getColumnIndexOrThrow(FeedUniversity.FeedStudent.COLUMN_NAME_BIRTHDATE)),
                        department = getString(getColumnIndexOrThrow(FeedUniversity.FeedStudent.COLUMN_NAME_DEPARTMENT)),
                        email = getString(getColumnIndexOrThrow(FeedUniversity.FeedStudent.COLUMN_NAME_EMAIL))
                    )
                )
            }
        }
        students.forEach {
            Log.d(QUERY_RESULT, it.toString())
        }
        return students
    }

    private fun selectStudent(selectModel: SelectModel): ArrayList<DatabaseSchemas>
    {
        val cursor = readable.query(
            FeedUniversity.FeedStudent.TABLE_NAME,   // The table to query
            null,             // The array of columns to return (pass null to get all)
            selectModel.selectionKey + " = ?",              // The columns for the WHERE clause
            selectModel.selectionValue,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )
        Log.d("Cursor", cursor.toString())
        val students = ArrayList<DatabaseSchemas>()
        with(cursor)
        {
            while (moveToNext())
            {
                students.add(
                    StudentModel(
                        id = getInt(getColumnIndexOrThrow(BaseColumns._ID)),
                        name = getString(getColumnIndexOrThrow(FeedUniversity.FeedStudent.COLUMN_NAME_NAME)),
                        surname = getString(getColumnIndexOrThrow(FeedUniversity.FeedStudent.COLUMN_NAME_SURNAME)),
                        password = getString(getColumnIndexOrThrow(FeedUniversity.FeedStudent.COLUMN_NAME_PASSWORD)),
                        isLecturer = getInt(getColumnIndexOrThrow(FeedUniversity.FeedStudent.COLUMN_NAME_IS_LECTURER)),
                        birthDate = getString(getColumnIndexOrThrow(FeedUniversity.FeedStudent.COLUMN_NAME_BIRTHDATE)),
                        department = getString(getColumnIndexOrThrow(FeedUniversity.FeedStudent.COLUMN_NAME_DEPARTMENT)),
                        email = getString(getColumnIndexOrThrow(FeedUniversity.FeedStudent.COLUMN_NAME_EMAIL))
                    )
                )
            }
        }
        students.forEach {
            Log.d(QUERY_RESULT, it.toString())
        }
        return students
    }

    private fun insertStudent(insertModel: InsertModel): Boolean
    {
        val queries = ArrayList<ContentValues>()
        insertModel.values.forEach { querySets ->
            val value = ContentValues()
            value.apply {
                put( BaseColumns._ID, (querySets[0] as Int) )
                put( FeedUniversity.FeedStudent.COLUMN_NAME_NAME, querySets[1] as String )
                put( FeedUniversity.FeedStudent.COLUMN_NAME_SURNAME, querySets[2] as String )
                put( FeedUniversity.FeedStudent.COLUMN_NAME_DEPARTMENT, querySets[3] as String )
                put( FeedUniversity.FeedStudent.COLUMN_NAME_BIRTHDATE, querySets[4] as String )
                put( FeedUniversity.FeedStudent.COLUMN_NAME_IS_LECTURER, querySets[5] as Int )
                put( FeedUniversity.FeedStudent.COLUMN_NAME_PASSWORD, querySets[6] as String )
                put( FeedUniversity.FeedStudent.COLUMN_NAME_EMAIL, querySets[7] as String )
            }
            queries.add(value)
        }
        writeable.beginTransaction();
        try{
            queries.forEach { query ->
                writeable?.insert(FeedUniversity.FeedStudent.TABLE_NAME, null, query)
            }
            writeable.setTransactionSuccessful()
        }
        catch (exc: Exception)
        {
            Log.d("INSERT", exc.stackTraceToString())
            return false
        }
        writeable.endTransaction()
        return true
    }

   private fun deleteStudent(selectModel: SelectModel): Boolean{

       try {
           writeable.delete(FeedUniversity.FeedStudent.TABLE_NAME, selectModel.selectionKey + " = ?", selectModel.selectionValue)
       }
       catch (exc: Exception){
           return false
       }

       return true
   }

    companion object {
        const val QUERY_RESULT = "Query Results"
    }
}