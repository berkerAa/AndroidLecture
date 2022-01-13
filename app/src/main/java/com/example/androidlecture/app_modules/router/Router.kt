package com.example.androidlecture.app_modules.router


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

interface Router <T> {
    fun <T> onActivityChange(activity: AppCompatActivity, instance: Class<T>) = activity.startActivity(Intent(activity.applicationContext, instance))
    fun <T> onFragmentChange(activity: AppCompatActivity,containerViewId: Int, instance: Class<T>, bundle: Bundle? = null) = activity.supportFragmentManager.beginTransaction()
        .replace(containerViewId, (instance.newInstance() as Fragment).apply { arguments = bundle })
        .setReorderingAllowed(true)
        .commit()
    fun  navigationLogic(activity: AppCompatActivity, navigationStatus: T, containerViewId: Int = 0, bundle: Bundle?)
    fun navigationLogicOnBackPressed(activity: AppCompatActivity, activeScreen: Fragment, containerViewId: Int = 0)
}