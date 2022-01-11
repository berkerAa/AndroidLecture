package com.example.androidlecture.app_modules.bridge

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.androidlecture.app_modules.router.Router
import com.example.androidlecture.R
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


open class Bridge<Component: Any, NavigationStatus: Any> : AppCompatActivity(){
    @Inject
    lateinit var router: Router<NavigationStatus>
    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    lateinit var component: Component

    fun <T: Fragment> startNavigation(factory: () -> T) {
        val entity: T = factory()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_holder, entity)
            .commit()
    }

    fun navigation(navigationStatus: NavigationStatus, containerViewId: Int = R.id.fragment_holder) = router.navigationLogic(activity = this, navigationStatus, containerViewId)
    override fun onBackPressed() =  router.navigationLogicOnBackPressed(this, supportFragmentManager.fragments.last(), R.id.fragment_holder)
    override fun onStop() =  compositeDisposable.clear().run{super.onStop()}
    override fun onDestroy() =  compositeDisposable.dispose().run { super.onDestroy() }

}