package com.example.androidlecture.core.dm


import com.example.androidlecture.app_modules.storage.SharedPreferencesStorage
import com.example.androidlecture.app_modules.storage.Storage
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class StorageModule {
    @Binds
    @Singleton
    abstract fun provideStorage(storage: SharedPreferencesStorage): Storage

}