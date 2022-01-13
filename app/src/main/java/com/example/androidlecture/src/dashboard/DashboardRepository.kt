package com.example.androidlecture.src.dashboard

import android.provider.BaseColumns
import android.util.Log
import com.example.androidlecture.app_modules.sequilezer.DatabaseQueryManager
import com.example.androidlecture.app_modules.sequilezer.model.SelectModel
import com.example.androidlecture.app_modules.sequilezer.model.StudentModel
import com.example.androidlecture.app_modules.sequilezer.response.BaseResponse
import com.example.androidlecture.data.database.FeedUniversity
import io.reactivex.Observable
import java.lang.Exception
import javax.inject.Inject

class DashboardRepository @Inject constructor(private val dbApi: DatabaseQueryManager) {

    fun pullEntries(departmentSelection: String): Observable<BaseResponse>
    {
        try {
            dbApi.select(
                SelectModel(
                    null,
                    selectionKey = FeedUniversity.FeedStudent.COLUMN_NAME_DEPARTMENT,
                    selectionValue = arrayOf(departmentSelection),
                    null,
                    null,
                    table = StudentModel()
                )
            ).run{
             return Observable.just(
                 BaseResponse(
                     status = 200,
                     data = this
                 ))
            }
        }
        catch (exc: Exception)
        {
            Log.d( DASHBOARD_REPOSITORY_TAG, exc.stackTraceToString() )
            return Observable.just(
                BaseResponse(
                    status = 404
                )
            )
        }

    }

    fun deleteEntry(entryId: String): Observable<BaseResponse>{

        try {
            dbApi.delete(
                SelectModel(
                    null,
                    selectionKey = BaseColumns._ID,
                    selectionValue = arrayOf(entryId),
                    null,
                    null,
                    table = StudentModel()
                )
            ).run {
                return Observable.just(
                    BaseResponse(
                    status = 200
                ))
            }
        }
        catch (
            exc: Exception
        ){
            Log.d( DASHBOARD_REPOSITORY_TAG, exc.stackTraceToString() )
            return Observable.just(
                BaseResponse(
                    status = 404
                )
            )
        }

    }

    companion object {
        const val DASHBOARD_REPOSITORY_TAG = "Dashboard Repository"
    }
}