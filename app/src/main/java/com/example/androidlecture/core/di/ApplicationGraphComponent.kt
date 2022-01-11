package com.example.androidlecture.core.di

import android.content.Context
import com.example.androidlecture.core.dm.NetworkModule
import com.example.androidlecture.core.dm.StorageModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class, StorageModule::class])
interface ApplicationGraphComponent {
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): ApplicationGraphComponent
    }

   // fun userComponent(): UserComponent.Factory
//@LogicFactoryProvider
}

