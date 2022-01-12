package com.example.androidlecture.src.user

import android.os.Bundle
import com.example.androidlecture.R
import com.example.androidlecture.src.user.di.UserComponent
import com.example.androidlecture.src.user.ds.UserScope
import com.example.androidlecture.app_modules.bridge.Bridge
import com.example.androidlecture.core.base.ApplicationGraph
import com.example.androidlecture.src.user.register.RegisterPresenter
import com.example.androidlecture.src.user.studentLogin.StudentLoginPresenter

@UserScope
class UserBridge: Bridge<UserComponent, UserNavigationStatus>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        component = (application as ApplicationGraph).applicationGraphComponent.userComponent().create()
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // startNavigation { StudentLoginPresenter() }

        startNavigation { StudentLoginPresenter() }
    }
}