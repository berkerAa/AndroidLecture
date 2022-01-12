package com.example.androidlecture.src.user.cases

sealed class FieldStatus {
}

object IncorrectValue: FieldStatus()
object CorrectValue: FieldStatus()
