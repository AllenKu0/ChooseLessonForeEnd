package com.example.rp0606.register

interface RegisterContract {
    interface View{
        fun registerProcess()

        fun registerFail()

        fun registerComplete()

        fun geToLoginActivity()
    }

    interface Presenter{
        fun register(registerRequest: RegisterRequest)
    }
}