package com.example.androidlecture.app_modules.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.androidlecture.databinding.StudentActivityLoginDatabindingBinding
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject



open class Presenter<T: ViewModel, E: ViewBinding>: Fragment() {

   /* @Inject
    lateinit var viewModel: T by injectActivityVIewModels()
    */
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    lateinit var viewBinding: E




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return viewBinding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
        super.onViewCreated(view, savedInstanceState)
        observeOn()
    }


    protected inline fun <reified T : ViewDataBinding> binding(
        inflater: LayoutInflater,
        @LayoutRes resId: Int,
        container: ViewGroup?
    ): T = DataBindingUtil.inflate(inflater, resId, container, false)

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    protected inline fun <reified VM : ViewModel>
            injectActivityVIewModels(): Lazy<VM> = activityViewModels { viewModelFactory }

    open fun observeOn(){}

    open fun initUI(){}
}