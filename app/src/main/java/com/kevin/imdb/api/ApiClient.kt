package com.kevin.imdb.api

import android.app.Activity
import android.content.Context
import com.kevin.imdb.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    //val serviceUrl = "https://api.themoviedb.org/3/movie/550?api_key=${BuildConfig.IMDBAPIKEY}"
    companion object {
        val serviceUrl = "https://api.themoviedb.org/3/"

        fun create(activity: Activity, context: Context): ApiMethods {

            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val httpClient = OkHttpClient().newBuilder()

            httpClient.readTimeout(3000, TimeUnit.SECONDS)
            httpClient.connectTimeout(3000, TimeUnit.SECONDS)
            httpClient.writeTimeout(3000, TimeUnit.SECONDS)

            httpClient.addInterceptor(logInterceptor)
            httpClient.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original = chain.request()

                    val request = original.newBuilder()
                        .header("Authorization", "Bearer ${BuildConfig.IMBTOKEN}")
                        .header("Content-Type", "application/json;charset=utf-8")
                        .method(original.method(), original.body())
                        .build()

                    return chain.proceed(request)
                }

            })

            val retrofitBuilder = Retrofit.Builder().apply {
                addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                addConverterFactory(GsonConverterFactory.create())
                baseUrl(serviceUrl)
                client(httpClient.build())
            }


            return retrofitBuilder.build().create(ApiMethods::class.java)

        }
    }
}