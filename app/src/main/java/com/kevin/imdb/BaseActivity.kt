package com.kevin.imdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kevin.imdb.utils.HttpCodeHandler
import com.kevin.imdb.utils.ProgressDialogHandler

open class BaseActivity : AppCompatActivity() {

    lateinit var httpCodeHandler : HttpCodeHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        httpCodeHandler = HttpCodeHandler(this, this)
        supportActionBar!!.hide()


    }

    fun showProgress(){

        ProgressDialogHandler.showProgress(this)
    }

    fun dismissProgress(){

        ProgressDialogHandler.dismissProgress()
    }

}