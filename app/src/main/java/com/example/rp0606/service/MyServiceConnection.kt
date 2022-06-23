package com.example.rp0606.service

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder


//service 連線
open class MyServiceConnection : ServiceConnection {
    var myService : MyService? = null
    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
        (p1 as MyService.MyBinder).getService().also { myService = it}
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        myService = null
    }
}