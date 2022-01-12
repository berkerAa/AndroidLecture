package com.example.androidlecture.core.dm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidlecture.core.base.AppViewModelFactory
import com.example.androidlecture.core.da.ViewModelKey
import com.example.androidlecture.src.user.register.RegisterViewModel
import com.example.androidlecture.src.user.studentLogin.StudentLoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(StudentLoginViewModel::class)
    internal abstract fun provideStudentLoginViewModel(studentLoginViewModel: StudentLoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    internal abstract fun provideRegisterViewModel(registerViewModel: RegisterViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}