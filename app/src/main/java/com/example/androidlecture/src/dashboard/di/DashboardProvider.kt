package com.example.androidlecture.src.dashboard.di

import com.example.androidlecture.src.user.di.UserComponent
import dagger.Module


@Module(subcomponents = [UserComponent::class])
object DashboardProvider {
}