package com.dangerfield.kind.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.dangerfield.kind.R
import kotlinx.android.synthetic.main.alert_custom.view.*

object AlertFactory {

    fun simpleAlert(
            context: Context, title: String, message: String, positiveText: String, positiveAction: (() -> Unit) = {},
            negativeText: String = "", negativeAction: (()-> Unit) = {}
    ): Dialog {

        val dialogBuilder = AlertDialog.Builder(context)
        val view = LayoutInflater.from(context).inflate(R.layout.alert_custom, null)
        dialogBuilder.setView(view)
        val dialog = dialogBuilder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true)

        view.apply {

            if(negativeText.isNotEmpty()){
                btn_negative.visibility = View.VISIBLE
                btn_negative.setOnClickListener { negativeAction.invoke(); dialog.cancel() }
                btn_negative.text = negativeText
            }

            btn_positive.setOnClickListener { positiveAction.invoke(); dialog.dismiss() }
            btn_positive.text = positiveText
            tv_alert_message.text = message
            tv_alert_title.text = title
        }
        return dialog
    }
}