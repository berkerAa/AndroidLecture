package com.example.androidlecture.core.di

import android.content.Context
import com.example.androidlecture.core.dm.DatabaseModule
import com.example.androidlecture.core.dm.NetworkModule
import com.example.androidlecture.core.dm.StorageModule
import com.example.androidlecture.core.dm.ViewModelModule
import com.example.androidlecture.src.dashboard.di.DashboardComponent
import com.example.androidlecture.src.dashboard.di.DashboardProvider
import com.example.androidlecture.src.user.di.UserComponent
import com.example.androidlecture.src.user.di.UserProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
    NetworkModule::class,
    StorageModule::class,
    ViewModelModule::class,
    DatabaseModule::class,
    UserProvider::class,
    DashboardProvider::class
])
interface ApplicationGraphComponent {
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): ApplicationGraphComponent
    }

    fun userComponent(): UserComponent.Factory
    fun dashboardComponent(): DashboardComponent.Factory
//@LogicFactoryProvider
}

