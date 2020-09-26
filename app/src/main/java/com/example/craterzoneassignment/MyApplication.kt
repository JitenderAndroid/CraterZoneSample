package com.example.craterzoneassignment

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

class MyApplication: Application() {

    fun getInstance(): MyApplication? {
        return myApplication
    }

    override fun onCreate() {
        super.onCreate()
        myApplication = this
    }

    companion object {
        private var myApplication: MyApplication? = null

        fun isNetworkAvailable(): Boolean {
            val connectivityManager = myApplication?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}