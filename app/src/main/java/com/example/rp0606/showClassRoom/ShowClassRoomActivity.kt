package com.example.rp0606.showClassRoom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.rp0606.BaseActivity
import com.example.rp0606.R
import com.example.rp0606.ShowMapActivity
import com.example.rp0606.showLesson.ShowLessonActivity
import com.example.rp0606.showLesson.ShowLessonResponse
import com.example.rp0606.showOffice.ShowOfficeResponse
import com.example.rp0606.showTeacher.ShowTeacherActivity

class ShowClassRoomActivity : BaseActivity(),ShowClassRoomContract.View {
    lateinit var classRoomName_txt:TextView
    lateinit var classLocation_txt:TextView
    lateinit var classRoomPhoneNumber_txt:TextView

    lateinit var showTeacher_btn:Button
    lateinit var map_btn:Button
    lateinit var back_img: ImageView
    val presenter:ShowClassRoomPresenter = ShowClassRoomPresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_class_room)

        classRoomName_txt = findViewById(R.id.classRoomName_txt)
        classLocation_txt = findViewById(R.id.classLocation_txt)
        classRoomPhoneNumber_txt = findViewById(R.id.classRoomPhoneNumber_txt)

        showTeacher_btn = findViewById(R.id.showTeacher_btn)
        map_btn = findViewById(R.id.map_btn)
        back_img = findViewById(R.id.back_img)

        showTeacher_btn.setOnClickListener(View.OnClickListener {
            goToShowTeacherActivity()
        })

        map_btn.setOnClickListener(View.OnClickListener {
            goToShowMapActivity()
        })

        back_img.setOnClickListener(View.OnClickListener {
            geToShowLessonActivity()
        })
    }

    override fun onResume() {
        super.onResume()
        val lessonDetail : ShowLessonResponse = intent.extras?.get("LessonDetail") as ShowLessonResponse
        presenter.getClassRoom(lessonDetail.lessonName)
    }

    override fun getClassRoomProcess() {
        showProgressDialog(this,"拿取課程資訊中")
    }

    override fun getClassRoomFail() {
        dismissProgressDialog()
        showToast(this,"拿取課程資訊失敗")
    }

    override fun getClassRoomComplete() {
        dismissProgressDialog()
//        showToast(this,"拿取課程資訊完成")
    }

    override fun setClassRoom(data: ShowClassRoomResponse?) {
        classRoomName_txt.text = data?.className
        classLocation_txt.text = data?.classLocation
        classRoomPhoneNumber_txt.text = data?.classRoomPhoneNumber
    }

    override fun backToShowLessonActivity() {
        finish()
    }

    override fun goToShowTeacherActivity() {
        intent.setClass(this,ShowTeacherActivity::class.java)
        startActivity(intent)
    }

    override fun goToShowMapActivity() {
        intent.setClass(this,ShowMapActivity::class.java)
        intent.putExtra("className",classRoomName_txt.text.toString())
        startActivity(intent)
    }

    override fun geToShowLessonActivity() {
        finish()
    }
}