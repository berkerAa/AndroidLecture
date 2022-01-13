package com.example.androidlecture.src.user.studentLogin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidlecture.app_modules.sequilezer.model.StudentModel
import com.example.androidlecture.app_modules.storage.Storage
import com.example.androidlecture.src.user.UserRepository
import com.example.androidlecture.src.user.cases.*
import com.example.androidlecture.src.user.data.StudentLoginModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.properties.Delegates

class StudentLoginViewModel @Inject constructor(private val userRepository: UserRepository, private val storage: Storage): ViewModel() {
    var studentId by Delegates.notNull<Int>()
    var password by Delegates.notNull<String>()
    private val _studentIdStatus: MutableLiveData<FieldStatus> by lazy {
        MutableLiveData<FieldStatus>()

    }
    val studentIdStatusLiveData: LiveData<FieldStatus>
        get() = _studentIdStatus

    fun setSession(studentModel: StudentModel) {
        storage.setModel(USER_SESSION, studentModel)
    }

    fun setStudentIdStatus(value: FieldStatus) = _studentIdStatus.setValue(value)


    fun onLogin(loginModel: StudentLoginModel) : Observable<RepositoryStatus>
    {
        return userRepository.onLogin(loginModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { BaseResponse ->
                when(BaseResponse.status)
                {
                    200 -> if (loginModel.studentPassword == (BaseResponse.data as StudentModel).password) setSession(BaseResponse.data).run { Success } else CredentialsError
                    500 -> CredentialsError
                    404 -> ConnectionError
                    else -> UnknownError
                }
            }
    }
    companion object {
        const val STUDENT_LOGIN_VIEW_MODEL = "STUDENT LOGIN VIEW MODEL"
        const val USER_SESSION = "user_session"
    }
}