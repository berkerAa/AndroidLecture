package com.example.androidlecture.src.dashboard

import android.os.Bundle
import com.example.androidlecture.R
import com.example.androidlecture.app_modules.bridge.Bridge
import com.example.androidlecture.core.base.ApplicationGraph
import com.example.androidlecture.src.dashboard.di.DashboardComponent
import com.example.androidlecture.src.dashboard.panel.PanelPresenter

class DashboardBridge : Bridge<DashboardComponent, DashboardNavigationStatus>(){

    override fun onCreate(savedInstanceState: Bundle?) {
        component = (application as ApplicationGraph).applicationGraphComponent.dashboardComponent().create()
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startNavigation { PanelPresenter() }
    }
}