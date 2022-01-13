package com.example.androidlecture.core.dm

import com.example.androidlecture.app_modules.router.Router
import com.example.androidlecture.src.dashboard.DashboardNavigationStatus
import com.example.androidlecture.src.dashboard.DashboardRouter
import com.example.androidlecture.src.dashboard.ds.DashboardScope
import com.example.androidlecture.src.user.UserNavigationStatus
import com.example.androidlecture.src.user.UserRouter
import com.example.androidlecture.src.user.ds.UserScope
import dagger.Binds
import dagger.Module


//@EndImportLibraries
@Module
abstract class RouterModule {


    @Binds @UserScope
    abstract fun provideUserRouter(routerProvider: UserRouter): Router<UserNavigationStatus>


    @Binds @DashboardScope
    abstract fun provideDashboardRouter(dashboardProvider: DashboardRouter): Router<DashboardNavigationStatus>
//@EndRouterBinder
}

