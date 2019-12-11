package com.kevin.imdb.api.dataSources

import android.app.Activity
import android.content.Context
import com.kevin.imdb.BuildConfig
import com.kevin.imdb.api.ApiClient
import com.kevin.imdb.api.OnResponseListener
import com.kevin.imdb.api.response.AuthenticateResponse
import retrofit2.Call
import retrofit2.Response

class AuthenticateDataSource(activity: Activity, context: Context) : DataSource(activity, context) {


    fun createSessionId(onResponseListener: OnResponseListener){
        ApiClient.create(activity, context).getSessionId(BuildConfig.IMDBAPIKEY).enqueue(object : retrofit2.Callback<AuthenticateResponse>{
            override fun onFailure(call: Call<AuthenticateResponse>, t: Throwable) {
                onResponseListener.onResponseFailure()
            }

            override fun onResponse(
                call: Call<AuthenticateResponse>,
                response: Response<AuthenticateResponse>
            ) {

                if (response.body() != null) {
                    var authenticateResponse = response.body() as AuthenticateResponse
                    if (authenticateResponse != null && response.code() == 200) {
                        onResponseListener.onResponseSuccess(
                            response.code(),
                            authenticateResponse
                        )
                    } else {
                        validateErrorResponse(response, onResponseListener)
                    }
                } else {
                    validateErrorResponse(response, onResponseListener)
                }
            }

        })
    }


}