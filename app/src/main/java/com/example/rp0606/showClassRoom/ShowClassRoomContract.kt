package com.example.rp0606.showClassRoom

import com.example.rp0606.showOffice.ShowOfficeResponse

interface ShowClassRoomContract {
    interface View{
        fun getClassRoomProcess()

        fun getClassRoomFail()

        fun getClassRoomComplete()

        fun setClassRoom(data: ShowClassRoomResponse?)

        fun backToShowLessonActivity()

        fun goToShowTeacherActivity()

    }
    interface Presenter{
        fun getClassRoom(lessonName:String)
    }
}