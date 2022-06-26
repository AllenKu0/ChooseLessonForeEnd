package com.example.rp0606.showLesson

interface ShowLessonContract {
    interface View{
        fun setLessonList(data:ArrayList<ShowLessonResponse>)

        fun setProfile(name:String?)

        fun onProcess(msg:String)

        fun onFail(msg:String)

        fun onComplete(msg:String)

        fun getLessonComplete(msg:String)

        fun dropOutLessonComplete(msg:String)

        fun goToShowClassRoomActivity(data:ShowLessonResponse)

        fun logout()
    }
    interface Presenter{
        fun getLessonList(account:String)

        fun dropOutLesson(account: String,lessonList: List<ShowLessonList>)

        fun getStudentProfile(account: String)
    }

    interface Adapter{
        fun setDataList(data:ArrayList<ShowLessonResponse>)

        fun getChooseLesson():ArrayList<ShowLessonList>

        fun getCreditCount():Int

        interface ViewHolder{
            fun bind(data:ArrayList<ShowLessonList>, position: Int, view:ShowLessonActivity)

        }
    }
}