package com.kevin.imdb.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by kevin on 3/6/19.
 */
class InternetUtils {
    companion object {

        @SuppressLint("MissingPermission")
        fun isInternetAvailable(activity : Activity): Boolean {
            val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

}