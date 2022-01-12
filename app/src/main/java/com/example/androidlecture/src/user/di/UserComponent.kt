package com.example.androidlecture.src.user.di
import com.example.androidlecture.src.user.ds.UserScope
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.deportak_mobile_application.core.dm.CompositeModule
import com.example.androidlecture.core.dm.RouterModule
import com.example.androidlecture.src.user.UserBridge
import com.example.androidlecture.src.user.register.RegisterPresenter
import com.example.androidlecture.src.user.studentLogin.StudentLoginPresenter
import dagger.Subcomponent

//@EndLibraryImport
@UserScope
@Subcomponent(modules = [ RouterModule::class, CompositeModule::class])
interface UserComponent {
    @Subcomponent.Factory
    interface Factory
    {
        fun create(): UserComponent
    }
    fun inject(activity: UserBridge)
    fun inject(fragment: StudentLoginPresenter)
    fun inject(fragment: RegisterPresenter)
}