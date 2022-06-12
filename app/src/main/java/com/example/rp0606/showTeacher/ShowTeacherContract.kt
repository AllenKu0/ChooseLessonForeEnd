package com.example.rp0606.showTeacher

interface ShowTeacherContract {
    interface View{
        fun setTeacherList(data:ArrayList<ShowTeacherResponse>)

        fun getTeacherProcess()

        fun getTeacherFail()

        fun getTeacherComplete()

        fun backToShowLessonActivity()

        fun geToShowOfficeActivity(position:Int)
    }
    interface Presenter{
        fun getTeacherList(lessonId:Long)
    }
    interface Adapter{
        fun setDataList(data:ArrayList<ShowTeacherResponse>)

        fun getDataList():ArrayList<ShowTeacherResponse>
        interface ViewHolder{
            fun bind(data:ArrayList<ShowTeacherResponse>,position:Int)
        }
    }

}