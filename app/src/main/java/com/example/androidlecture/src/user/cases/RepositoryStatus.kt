package com.example.androidlecture.src.user.cases

sealed class RepositoryStatus

object Success: RepositoryStatus()

data class SuccessWithData @JvmOverloads constructor(val data: Any = ""): RepositoryStatus()

object CredentialsError: RepositoryStatus()

object ConnectionError: RepositoryStatus()

object UnknownError: RepositoryStatus()
