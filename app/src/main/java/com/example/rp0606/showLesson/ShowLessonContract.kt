package com.example.rp0606.showLesson

interface ShowLessonContract {
    interface View{
        fun setLessonList(data:ArrayList<ShowLessonResponse>)

        fun getLessonProcess()

        fun getLessonFail()

        fun getLessonComplete()

        fun goToShowTeacherActivity(data:ShowLessonResponse)
    }
    interface Presenter{
        fun getLessonList(account:String)
    }

    interface Adapter{
        fun setDataList(data:ArrayList<ShowLessonResponse>)

        interface ViewHolder{
            fun bind(data:ArrayList<ShowLessonResponse>, position: Int,view:ShowLessonActivity)

        }
    }
}