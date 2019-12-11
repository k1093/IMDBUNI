package com.kevin.imdb

import com.kevin.imdb.api.OnResponseListener

interface BaseInterface {

    fun setOnResponseListener(onResponseListener: OnResponseListener)
}