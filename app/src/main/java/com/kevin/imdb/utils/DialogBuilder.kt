package com.kevin.imdb.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.kevin.imdb.R

class DialogBuilder {

    companion object{
        fun createSimpleDialog(title: String?, content: String?, textPositive: String, textNegative: String?, context: Context, confirmCallBack: InterfaceDialog?) : AlertDialog {

            val dialogBuilder = AlertDialog.Builder(context)
            var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val dialogView = inflater.inflate(R.layout.alert_common, null)
            var textTitle = dialogView.findViewById<TextView>(R.id.text_title)
            var textMessage = dialogView.findViewById<TextView>(R.id.text_message)
            textTitle.setText(title)
            if (title.isNullOrEmpty()) {
                textTitle.visibility = View.GONE
            }
            textMessage.setText(content)
            dialogBuilder.setView(dialogView)
            dialogBuilder.setCancelable(false)
                .setPositiveButton(textPositive) { dialogInterface, i ->
                    if(confirmCallBack != null) {
                        confirmCallBack.onConfirm()
                    }
                    dialogInterface.cancel()
                }
            if (textNegative != null && !textNegative!!.isEmpty())
                dialogBuilder.setNegativeButton(textNegative) { dialogInterface, i ->
                    if(confirmCallBack != null) {
                        confirmCallBack.onCancel()
                    }

                    dialogInterface.cancel()
                }


            var alertDialogB = dialogBuilder.create()
            alertDialogB.show()

            return alertDialogB
        }
    }

    interface InterfaceDialog {
        fun onConfirm()
        fun onCancel()
    }
}