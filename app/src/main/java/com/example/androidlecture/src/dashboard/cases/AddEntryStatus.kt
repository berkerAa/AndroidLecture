package com.example.androidlecture.src.dashboard.cases

sealed class AddEntryStatus

object ProfileSelection: AddEntryStatus()
object Init: AddEntryStatus()
object EntryBody: AddEntryStatus()
object PasswordPopUp: AddEntryStatus()