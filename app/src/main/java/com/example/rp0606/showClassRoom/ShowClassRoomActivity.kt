package com.example.rp0606.showClassRoom

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.rp0606.BaseActivity
import com.example.rp0606.MainApplication
import com.example.rp0606.R
import com.example.rp0606.map.ShowMapActivity
import com.example.rp0606.showLesson.ShowLessonResponse
import com.example.rp0606.showTeacher.ShowTeacherActivity

class ShowClassRoomActivity : BaseActivity(),ShowClassRoomContract.View {
    private  val TAG = "ShowClassRoomActivity"
    lateinit var classRoomName_txt:TextView
    lateinit var classLocation_txt:TextView
    lateinit var classRoomPhoneNumber_txt:TextView

    lateinit var showTeacher_btn:Button
    lateinit var back_img: ImageView

    lateinit var toolbar:Toolbar

    lateinit var onClickListener: DialogInterface.OnClickListener

    val presenter:ShowClassRoomPresenter = ShowClassRoomPresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_class_room)

        toolbar = findViewById(R.id.classRoom_toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        classRoomName_txt = findViewById(R.id.classRoomName_txt)
        classLocation_txt = findViewById(R.id.classLocation_txt)
        classRoomPhoneNumber_txt = findViewById(R.id.classRoomPhoneNumber_txt)

        showTeacher_btn = findViewById(R.id.showTeacher_btn)
        back_img = findViewById(R.id.back_img)

        showTeacher_btn.setOnClickListener(View.OnClickListener {
            goToShowTeacherActivity()
        })

        back_img.setOnClickListener(View.OnClickListener {
            geToShowLessonActivity()
        })

        onClickListener = object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val lessonDetail : ShowLessonResponse = intent.extras?.get("LessonDetail") as ShowLessonResponse
        if (getNetWorkState(MainApplication.applicationContext()) == -1) {
            showOnSureDialog(onClickListener,"請開啟網路")
        } else{
            presenter.getClassRoom(lessonDetail.lessonName)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.classroom_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.show_map -> goToShowMapActivity()
            else -> Log.e(TAG, "onOptionsItemSelected: ${item.itemId}")
        }
        return super.onOptionsItemSelected(item)
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
        intent.setClass(this, ShowMapActivity::class.java)
        intent.putExtra("className",classRoomName_txt.text.toString())
        startActivity(intent)
    }

    override fun geToShowLessonActivity() {
        finish()
    }
}