package com.example.rp0606.service

import android.app.DownloadManager.Request.NETWORK_MOBILE
import android.app.DownloadManager.Request.NETWORK_WIFI
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Binder
import android.os.IBinder
import android.os.Parcelable
import android.util.Log
import com.example.rp0606.MainApplication

class MyService : Service() {

    companion object {
        const val NETWORK_NONE = -1
    }

    val myBinder: MyBinder = MyBinder(this)
//    val receiver: MyService.Receiver = MyService.Receiver()

    override fun onBind(p0: Intent?): IBinder? {
        return myBinder
    }

    override fun onCreate() {
        super.onCreate()
//        val intentFilter: IntentFilter = IntentFilter()
//        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
//        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(receiver, intentFilter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        stopSelf()
        super.onDestroy()
    }

    open class MyBinder(private val myService: MyService) : Binder() {
        open fun getService(): MyService {
            return myService
        }
    }

//    open class Receiver : BroadcastReceiver() {
//        override fun onReceive(p0: Context?, p1: Intent?) {
//            if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(p1?.action)) {
//                val info: NetworkInfo? =
//                    p1?.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO)
//                if (null != info) {
//                    val netWorkInfo: NetworkInfo
//                    if (NetworkInfo.State.CONNECTED == info.state && info.isAvailable) {
//                        if (info.getType() == ConnectivityManager.TYPE_WIFI || info.getType() == ConnectivityManager.TYPE_MOBILE) {
//                            Log.e("TAG", info.getType().toString() + "已连接");
//                        } else {
//                            Log.e("TAG", info.getType().toString() + "已断开");
//                        }
//                    }
//                }
//            }
//        }
//    }
}