package com.example.rp0606.alertPassword

interface AlertPasswordContract {
    interface Presenter{
        fun alertPassword(account:String,newPassword:String)
    }
    interface View{
        fun alertProcess(msg:String)

        fun alertComplete()

        fun alertFail(msg:String)
    }
}