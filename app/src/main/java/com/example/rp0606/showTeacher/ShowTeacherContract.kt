package com.example.rp0606.showTeacher

import com.example.rp0606.showOffice.ShowOfficeResponse

interface ShowTeacherContract {
    interface View{
        fun setTeacherList(data:ArrayList<ShowTeacherResponse>)

        fun getTeacherProcess()

        fun getTeacherFail()

        fun getTeacherComplete()

        fun backToShowLessonActivity()

        fun geToShowOfficeActivity(position:Int)


        //

        fun getOfficeProcess()

        fun getOfficeFail()

        fun getOfficeComplete()

        fun getOfficeDetail(teacherName:String)

        fun showOfficeDetail(t: ShowOfficeResponse?)


    }
    interface Presenter{
        fun getTeacherList(lessonId:Long)

        fun getOfficeList(teacherName: String)
    }
    interface Adapter{
        fun setDataList(data:ArrayList<ShowTeacherResponse>)

        fun getDataList():ArrayList<ShowTeacherResponse>
        interface ViewHolder{
            fun bind(data:ArrayList<ShowTeacherResponse>,position:Int)
        }
    }

}