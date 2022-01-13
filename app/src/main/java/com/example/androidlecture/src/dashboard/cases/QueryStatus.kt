package com.example.androidlecture.src.dashboard.cases

import com.example.androidlecture.app_modules.sequilezer.model.StudentModel

sealed class QueryStatus

object InProgress : QueryStatus()
object FinishedError : QueryStatus()
data class FinishedSuccess ( val data: ArrayList<StudentModel> ): QueryStatus()