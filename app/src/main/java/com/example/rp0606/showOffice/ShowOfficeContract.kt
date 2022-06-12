package com.example.rp0606.showOffice

interface ShowOfficeContract {
    interface View{
        fun getOfficeProcess()

        fun getOfficeFail()

        fun getOfficeComplete()

        fun setOfficeList(data:ShowOfficeResponse?)

        fun backToShowTeacherActivity()

    }
    interface Presenter{
        fun getOfficeList(teacherName:String)
    }
}