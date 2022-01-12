package com.example.androidlecture.core.dm

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.androidlecture.app_modules.sequilezer.DatabaseQueryManager
import com.example.androidlecture.data.database.FeedStudentHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): DatabaseQueryManager {
        return DatabaseQueryManager( FeedStudentHelper(context) )
    }

}