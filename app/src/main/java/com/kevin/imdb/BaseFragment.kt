package com.kevin.imdb

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kevin.imdb.utils.HttpCodeHandler
import com.kevin.imdb.utils.ProgressDialogHandler

open class BaseFragment : Fragment() {

    lateinit var httpCodeHandler : HttpCodeHandler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        httpCodeHandler = HttpCodeHandler(context!!, activity!!)
    }

    fun showProgress(){

        ProgressDialogHandler.showProgress(context!!)
    }

    fun dismissProgress(){

        ProgressDialogHandler.dismissProgress()
    }
}