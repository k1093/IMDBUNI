package com.kevin.imdb.api.dataSources

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import com.kevin.imdb.api.OnResponseListener
import com.kevin.imdb.api.response.ErrorResponse
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception

open class DataSource(activity: Activity, context: Context) {

    var activity = activity
    var context = context

    fun getInfoFromError(responseBody: ResponseBody): ErrorResponse {

        var gson = Gson()
        var errorResponse = gson.fromJson(responseBody.string(), ErrorResponse::class.java)

        return errorResponse
    }

    fun<T> validateErrorResponse(response : Response<T>, onResponseListener: OnResponseListener){

        if(isAFailureCode(response.code())){
            if(response.errorBody() != null){
                try {
                    onResponseListener.onResponseHasErrors(response.code(), getInfoFromError(response.errorBody()!!))
                }catch (e : Exception){
                    onResponseListener.onResponseFailure()
                }

            }else{
                onResponseListener.onResponseFailure()
            }
        }else{
            onResponseListener.onResponseFailure()
        }

    }



    fun isAFailureCode(errorCode : Int) = when(errorCode){
        404, 502, 503, 504, 401, 400, 402, 403, 500 -> true
        else -> false
    }

}