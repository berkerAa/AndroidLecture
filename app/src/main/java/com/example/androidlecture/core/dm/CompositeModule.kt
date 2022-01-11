package com.deportak_mobile_application.core.dm

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class CompositeModule {

    @Provides
    fun compositeDisposable(): CompositeDisposable
    {
        return CompositeDisposable()
    }
}