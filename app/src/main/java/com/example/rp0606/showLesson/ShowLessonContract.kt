package com.example.rp0606.showLesson

interface ShowLessonContract {
    interface View{
        fun setLessonList(data:ArrayList<ShowLessonResponse>)

        fun onProcess(msg:String)

        fun onFail(msg:String)

        fun onComplete(msg:String)

        fun dropOutLessonComplete(msg:String)

        fun goToShowClassRoomActivity(data:ShowLessonResponse)

        fun logout()
    }
    interface Presenter{
        fun getLessonList(account:String)

        fun dropOutLesson(account: String,lessonList: List<ShowLessonList>)
    }

    interface Adapter{
        fun setDataList(data:ArrayList<ShowLessonResponse>)

        fun getChooseLesson():ArrayList<ShowLessonList>

        interface ViewHolder{
            fun bind(data:ArrayList<ShowLessonList>, position: Int, view:ShowLessonActivity)

        }
    }
}