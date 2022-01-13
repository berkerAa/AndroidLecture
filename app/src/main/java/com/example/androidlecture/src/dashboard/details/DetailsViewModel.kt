package com.example.androidlecture.src.dashboard.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidlecture.app_modules.sequilezer.DatabaseQueryManager.Companion.QUERY_RESULT
import com.example.androidlecture.app_modules.sequilezer.model.StudentModel
import com.example.androidlecture.app_modules.storage.Storage
import com.example.androidlecture.src.dashboard.DashboardRepository
import com.example.androidlecture.src.dashboard.cases.*
import com.example.androidlecture.src.user.UserRepository
import com.example.androidlecture.src.user.cases.*
import com.example.androidlecture.src.user.data.RegisterModel
import com.example.androidlecture.src.user.studentLogin.StudentLoginViewModel.Companion.USER_SESSION
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val dashboardRepository: DashboardRepository, private val userRepository: UserRepository, private val storage: Storage): ViewModel() {

    private val _queryStatus: MutableLiveData<QueryStatus> by lazy {
        MutableLiveData()
    }
    private val _entryStatus: MutableLiveData<AddEntryStatus> by lazy {
        MutableLiveData(ProfileSelection)
    }
    val queryStatus: LiveData<QueryStatus>
        get() = _queryStatus
    val entryStatus: LiveData<AddEntryStatus>
        get() = _entryStatus

    fun checkAccess() : Boolean{
        val result  = storage.getModel(USER_SESSION, StudentModel::class.java)
        Log.d(QUERY_RESULT, result.toString())
        return result.isLecturer == 1
    }
    fun setQueryStatus(status: QueryStatus) = _queryStatus.postValue(status)

    fun setEntryStatus(status: AddEntryStatus) = _entryStatus.setValue(status)
    fun postEntryStatus(status: AddEntryStatus) = _entryStatus.postValue(status)
    fun pullItems(departmentSelection: String): Observable<QueryStatus> {
        _queryStatus.postValue(InProgress)
        return dashboardRepository.pullEntries(departmentSelection)
            .subscribeOn(io())
            .observeOn(io())
            .map { response ->
                    when (response.status){
                        200 -> FinishedSuccess(response.data as ArrayList<StudentModel>)
                        404 -> FinishedError
                        else -> FinishedError
                    }
            }
    }

    fun addUser(registerModel: RegisterModel): Observable<RepositoryStatus> {

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

    fun deleteUser(studentId: String): Observable<RepositoryStatus> {
        return dashboardRepository.deleteEntry(studentId)
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .map{ BaseResponse ->

                when(BaseResponse.status)
                {
                    200 -> Success
                    404 -> ConnectionError
                    else -> UnknownError
                }
            }
    }



}