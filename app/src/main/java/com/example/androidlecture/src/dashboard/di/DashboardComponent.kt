package com.example.androidlecture.src.dashboard.di

import com.deportak_mobile_application.core.dm.CompositeModule
import com.example.androidlecture.core.dm.RouterModule
import com.example.androidlecture.src.dashboard.DashboardBridge
import com.example.androidlecture.src.dashboard.details.DetailsPresenter
import com.example.androidlecture.src.dashboard.ds.DashboardScope
import com.example.androidlecture.src.dashboard.panel.PanelPresenter
import dagger.Subcomponent

@DashboardScope
@Subcomponent(modules = [ RouterModule::class, CompositeModule::class])
interface DashboardComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): DashboardComponent
    }

    fun inject(activity: DashboardBridge)
    fun inject(fragment: PanelPresenter)
    fun inject(fragment: DetailsPresenter)
}