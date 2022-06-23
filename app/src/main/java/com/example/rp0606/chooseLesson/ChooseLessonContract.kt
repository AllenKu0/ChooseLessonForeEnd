package com.example.rp0606.chooseLesson

import androidx.annotation.LayoutRes
import com.example.rp0606.showLesson.ShowLessonResponse

interface ChooseLessonContract {
    interface View {
        fun setAllLessonList(data:ArrayList<ChooseLessonResponse>)

        fun getNotSelectProcess()

        fun getNotSelectError()

        fun getNotSelectComplete()

        fun getLessonProcess()

        fun getLessonError()

        fun getLessonComplete()

        fun chooseLessonProcess()

        fun chooseLessonError()

        fun chooseLessonComplete()

        fun backToShowLessonActivity()

        fun showHintDialog(@LayoutRes layoutId: Int)
    }
    interface Presenter{
        fun getNotSelectLesson(account:String)

        fun getAllLesson()

        fun chooseLesson(data:ArrayList<ChooseLessonRequest>)
    }
    interface Adapter{
        fun getChooseLesson():ArrayList<ChooseLessonRequest>
    }
}