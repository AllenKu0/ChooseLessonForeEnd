package com.example.rp0606.showProfile

interface ShowProfileContract {
    interface Presenter{
        fun getProfile(account:String)

        fun upDateProfile(account: String,showProfileRequest:ShowProfileRequest)
    }
    interface View{
        fun setProfile(data: ShowProfileResponse?)

        fun onErrorSolution(msg:String)

        fun onCompleteSolution()

        fun onPrecessSolution(msg:String)

        fun updateComplete()

    }
}