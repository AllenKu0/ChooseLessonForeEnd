package com.example.rp0606.showLesson

import androidx.core.content.contentValuesOf
import java.io.Serializable

data class ShowLessonResponse(val lessonId:Long, val lessonName:String, val lessonCredit:Int,val lessonStatus:String): Serializable {

}
