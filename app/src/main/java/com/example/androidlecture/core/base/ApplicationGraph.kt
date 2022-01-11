package com.example.androidlecture.core.base

import android.app.Application
import com.example.androidlecture.core.di.ApplicationGraphComponent
import com.example.androidlecture.core.di.DaggerApplicationGraphComponent

open class ApplicationGraph: Application() {
    val applicationGraphComponent: ApplicationGraphComponent by lazy {
        DaggerApplicationGraphComponent.factory().create(applicationContext)
    }
}