package com.example.rp0606.login

interface LoginContract {
    interface View{
        fun loginSuccess()

        fun loginError()

        fun loginProcess()
    }

    interface Presenter{
        fun loginApi(userLogin: LoginRequest)
    }
}