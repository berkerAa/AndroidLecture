package com.example.androidlecture.src.user

import android.provider.BaseColumns
import android.util.Log
import com.example.androidlecture.app_modules.sequilezer.DatabaseQueryManager
import com.example.androidlecture.app_modules.sequilezer.model.InsertModel
import com.example.androidlecture.app_modules.sequilezer.model.SelectModel
import com.example.androidlecture.app_modules.sequilezer.model.StudentModel
import com.example.androidlecture.app_modules.sequilezer.response.BaseResponse
import com.example.androidlecture.src.user.data.RegisterModel
import com.example.androidlecture.src.user.data.StudentLoginModel
import io.reactivex.Observable
import com.example.androidlecture.src.user.ds.UserScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import java.util.*
import javax.inject.Inject
import kotlin.NoSuchElementException
import kotlin.math.pow


class UserRepository @Inject constructor(private val dbApi: DatabaseQueryManager) {

    private fun Boolean.toInt() = if (this) 1 else 0
    fun onLogin(loginModel: StudentLoginModel): Observable<BaseResponse> {
        try {
            dbApi.select(
                SelectModel(
                    selectionKey = BaseColumns._ID,
                    selectionValue = arrayOf(loginModel.studentId),
                    table = StudentModel()
                )
            ).apply {
                return Observable.just(
                    BaseResponse(
                        status = 200,
                        data = first()
                ))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }
        catch (exc: Exception){
            Log.d(USER_REPOSITORY_TAG, exc.stackTraceToString())
            return when (exc) {
                is NoSuchElementException -> Observable.just(
                    BaseResponse(
                        status = 500
                    )
                )
                else -> Observable.just(
                    BaseResponse(
                        status = 404
                    )
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }

    fun onRegister( registerModel: RegisterModel ) : Observable<BaseResponse>
    {
        val _id = Random().nextInt(10.toDouble().pow(9).toInt() -1) + 10.toDouble().pow(9).toInt()
        val insertModel = InsertModel(
            table = StudentModel(),
            values = arrayListOf(
                arrayListOf(
                    _id,
                    registerModel.name,
                    registerModel.surname,
                    registerModel.department,
                    registerModel.birthDate,
                    registerModel.isLecturer.toInt(),
                    registerModel.password,
                    registerModel.email
                )
            )

        )
        return try{
            val response =  if ( dbApi.insert(insertModel) ) BaseResponse(status = 200, data = _id) else BaseResponse(status = 501)
            Observable.just(response)
        } catch (exc: Exception) {
            Log.d(USER_REPOSITORY_TAG, exc.stackTraceToString())
            Observable.just(
                BaseResponse(
                    status = 404
                ))
        }
    }

    companion object {
        const val USER_REPOSITORY_TAG = "USER REPOSITORY"
    }

	//@EndRepositoryConnections
}