package com.example.androidlecture.src.user

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.androidlecture.app_modules.router.Router
import com.example.androidlecture.src.user.ds.UserScope
import com.example.androidlecture.src.user.register.RegisterPresenter
import com.example.androidlecture.src.user.studentLogin.StudentLoginPresenter
import javax.inject.Inject

//@EndLibraryImport

sealed class UserNavigationStatus
data class Login(val STUDENT_LOGIN: Class<StudentLoginPresenter> = StudentLoginPresenter::class.java): UserNavigationStatus()
data class Register(val REGISTER: Class<RegisterPresenter> = RegisterPresenter::class.java): UserNavigationStatus()


@UserScope
class UserRouter @Inject constructor(): Router<UserNavigationStatus> {
    override fun navigationLogic(
        activity: AppCompatActivity,
        navigationStatus: UserNavigationStatus,
        containerViewId: Int,
    ) {
        when(navigationStatus)
        {
		    is Login -> onFragmentChange(activity, containerViewId, navigationStatus.STUDENT_LOGIN)
            is Register -> onFragmentChange(activity, containerViewId, navigationStatus.REGISTER)
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
            is StudentLoginPresenter -> activity.finishAffinity()
            is RegisterPresenter -> onFragmentChange(activity, containerViewId, Login().STUDENT_LOGIN)
            
        }
    }
}