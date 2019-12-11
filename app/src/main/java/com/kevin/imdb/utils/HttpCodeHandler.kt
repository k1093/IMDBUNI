package com.kevin.imdb.utils

import android.app.Activity
import android.content.Context
import com.kevin.imdb.api.response.ErrorResponse

class HttpCodeHandler(context: Context, activity: Activity) {

    private var context = context
    private var activity = activity

    fun showSucessMessage( interfaceDialog: DialogBuilder.InterfaceDialog?){

        if(interfaceDialog != null) {
            DialogBuilder.createSimpleDialog(
                "IMBDB",
                "Exito!!",
                "Aceptar",
                "",
                context,
                interfaceDialog
            )
        }

    }


    fun showHttpMessage( httpCode : Int, interfaceDialog: DialogBuilder.InterfaceDialog?){

        if(httpCode == 401){
            DialogBuilder.createSimpleDialog("IMBDB","No tienes acceso a IMBDB", "Aceptar", "", context, object : DialogBuilder.InterfaceDialog{
                override fun onConfirm() {
                    getHttpAction(httpCode)
                }

                override fun onCancel() {
                    getHttpAction(httpCode)
                }

            })
        }else{
            DialogBuilder.createSimpleDialog("IMBDB","Error ${httpCode}", "Aceptar", "", context, object : DialogBuilder.InterfaceDialog{
                override fun onConfirm() {
                    getHttpAction(httpCode)
                }

                override fun onCancel() {
                    getHttpAction(httpCode)
                }

            })
        }

    }


    fun showGenerarlFailureMessage(interfaceDialog: DialogBuilder.InterfaceDialog?){
        DialogBuilder.createSimpleDialog("Fri", "No se ha podido concluir con la operación, por favor intente más tarde.", "Aceptar", "", context, interfaceDialog )
    }


    fun getHttpAction( httpCode: Int){

        when(httpCode){
            401 ->{
                //Sacar de la sesion
            }

        }
    }

}