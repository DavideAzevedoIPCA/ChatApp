package com.example.chatapp

import android.app.Application
import android.content.Context

class MyApplication: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null
        //var appContext: Context = instance!!.applicationContext

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        val context : Context = MyApplication.applicationContext()
        //MyApplication.appContext = applicationContext()
    }
}