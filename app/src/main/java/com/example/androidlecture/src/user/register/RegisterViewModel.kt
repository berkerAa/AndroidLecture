package com.example.androidlecture.src.user.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidlecture.app_modules.sequilezer.model.StudentModel
import com.example.androidlecture.src.user.UserRepository
import com.example.androidlecture.src.user.cases.*
import com.example.androidlecture.src.user.data.RegisterModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RegisterViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    private val _profile: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }
    var name = ""
    var surname = ""
    var birthDate = ""
    var password = ""
    var department = ""
    var email =  ""
    var isLecturer: Boolean = false
    val profile: LiveData<Boolean>
        get() = _profile

    fun onRegister(registerModel: RegisterModel): Observable<RepositoryStatus>
    {
        return userRepository.onRegister(registerModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map{ BaseResponse ->

                when(BaseResponse.status)
                {
                    200 -> SuccessWithData( data =( BaseResponse.data as Int ) )
                    501 -> CredentialsError
                    404 -> ConnectionError
                    else -> UnknownError
                }
            }
    }

    fun setProfile(value: Boolean) = _profile.setValue(value)
}