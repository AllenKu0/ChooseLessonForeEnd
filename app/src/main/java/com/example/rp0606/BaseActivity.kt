package com.example.rp0606

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {
    lateinit var alertBuilder:AlertDialog.Builder
    lateinit var alertDialog:AlertDialog
    fun showProgressDialog(context: Context,msg:String){
        Log.e("TAG", "showProgressDialog: " )
        val view = LayoutInflater.from(this).inflate(R.layout.login_progress,null)
        alertBuilder = AlertDialog.Builder(context)
        alertBuilder.setView(view)
        alertDialog = alertBuilder.create()
        val progressMsg_txt:TextView = view.findViewById(R.id.progressMsg_txt)
        progressMsg_txt.text= msg
        alertDialog.show()
    }

    fun dismissProgressDialog(){
        Log.e("TAG", "dismissProgressDialog: " )
        if(alertDialog!=null && alertDialog.isShowing){
            alertDialog.dismiss()
        }
    }

    fun showToast(context: Context,msg: String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }
}