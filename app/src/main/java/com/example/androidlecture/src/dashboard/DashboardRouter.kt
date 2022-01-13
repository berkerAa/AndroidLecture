package com.example.androidlecture.src.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.androidlecture.app_modules.router.Router
import com.example.androidlecture.src.dashboard.details.DetailsPresenter
import com.example.androidlecture.src.dashboard.ds.DashboardScope
import com.example.androidlecture.src.dashboard.panel.PanelPresenter
import com.example.androidlecture.src.user.Login
import com.example.androidlecture.src.user.UserNavigationStatus
import com.example.androidlecture.src.user.ds.UserScope
import com.example.androidlecture.src.user.register.RegisterPresenter
import com.example.androidlecture.src.user.studentLogin.StudentLoginPresenter
import javax.inject.Inject

sealed class DashboardNavigationStatus
data class Panel(val PANEL: Class<PanelPresenter> = PanelPresenter::class.java): DashboardNavigationStatus()
data class Details(val DETAILS: Class<DetailsPresenter> = DetailsPresenter::class.java): DashboardNavigationStatus()

@DashboardScope
class DashboardRouter @Inject constructor(): Router<DashboardNavigationStatus> {

    override fun navigationLogic(
        activity: AppCompatActivity,
        navigationStatus: DashboardNavigationStatus,
        containerViewId: Int,
        bundle: Bundle?
    ) {
        when(navigationStatus)
        {
            is Panel ->  onFragmentChange(activity, containerViewId, navigationStatus.PANEL, bundle)
            is Details ->  onFragmentChange(activity, containerViewId, navigationStatus.DETAILS, bundle)
            //@EndNavigationCaseCheck
        }
    }
    override fun navigationLogicOnBackPressed(
        activity: AppCompatActivity,
        activeScreen: Fragment,
        containerViewId: Int,
    ) {
        when(activeScreen)
        {
            is PanelPresenter -> activity.finishAffinity()
            is DetailsPresenter ->  onFragmentChange(activity, containerViewId, Panel().PANEL)

        }
    }


}