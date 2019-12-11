package com.kevin.imdb.api

import com.kevin.imdb.api.response.ErrorResponse


interface OnResponseListener {

    fun onResponseSuccess(code : Int, response : Any)
    fun onResponseHasErrors(code : Int, errorResponse: ErrorResponse)
    fun onResponseFailure()
}