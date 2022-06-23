package com.example.rp0606

import android.app.DownloadManager
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.rp0606.service.MyService

open class BaseActivity : AppCompatActivity() {
    lateinit var alertBuilder: AlertDialog.Builder
    lateinit var alertDialog: AlertDialog
    fun showProgressDialog(context: Context, msg: String) {
        Log.e("TAG", "showProgressDialog: ")
        val view = LayoutInflater.from(this).inflate(R.layout.login_progress, null)
        alertBuilder = AlertDialog.Builder(context)
        alertBuilder.setView(view)
        alertDialog = alertBuilder.create()
        if(!(alertDialog!=null && alertDialog.isShowing)){
            Log.e("TAG", "showProgressDialog: real" + alertDialog.isShowing)
            val progressMsg_txt: TextView = view.findViewById(R.id.progressMsg_txt)
            progressMsg_txt.text = msg
            alertDialog.show()
        }
    }

    fun dismissProgressDialog() {
        Log.e("TAG", "dismissProgressDialog: "+alertDialog.isShowing)
        if (alertDialog != null && alertDialog.isShowing) {
            Log.e("TAG", "dismissProgressDialog: real")
            alertDialog.dismiss()
        }
    }

    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun showOnSureDialog(onClickListener: DialogInterface.OnClickListener, message: String) {
        AlertDialog.Builder(this)
            .setTitle(message)
            .setCancelable(false)
            .setPositiveButton(android.R.string.yes, onClickListener)
            .show()
    }

    fun showChooseDialog(onClickListener: DialogInterface.OnClickListener, message: String) {
        AlertDialog.Builder(this)
            .setTitle(message)
            .setCancelable(false)
            .setPositiveButton(android.R.string.yes, onClickListener)
            .setNegativeButton(android.R.string.no,null)
            .show()
    }

    fun showDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle(message)
            .setCancelable(false)
            .setPositiveButton(android.R.string.yes, null)
            .show()
    }
    //檢查連線狀態
    fun getNetWorkState(context: Context): Int {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return DownloadManager.Request.NETWORK_WIFI;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return DownloadManager.Request.NETWORK_MOBILE;
            }
        } else {
            return MyService.NETWORK_NONE;
        }
        return MyService.NETWORK_NONE;
    }

//    fun showCustomDialog(@LayoutRes layoutId:Int) {
//        val inflater = LayoutInflater.from(MainApplication.applicationContext())
//        val view = inflater.inflate(layoutId,null)
//        AlertDialog.Builder(this)
//            .setView(view)
//            .setCancelable(false)
//            .show()
//
//    }
}