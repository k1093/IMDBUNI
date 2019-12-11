package com.kevin.imdb.utils

import android.app.ProgressDialog
import android.content.Context
import com.kevin.imdb.R

class ProgressDialogHandler {

    companion object{

        private lateinit var dialog : ProgressDialog

        fun showProgress(context: Context){

            dialog = ProgressDialog.show(context, "", context.getString(R.string.title_wait_please))
            dialog.show()
        }

        fun dismissProgress(){

            if(dialog != null && dialog.isShowing){
                dialog.dismiss()
            }

        }
    }
}