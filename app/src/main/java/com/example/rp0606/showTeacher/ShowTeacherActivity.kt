package com.example.rp0606.showTeacher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rp0606.BaseActivity
import com.example.rp0606.R
import com.example.rp0606.showLesson.ShowLessonActivity
import com.example.rp0606.showLesson.ShowLessonResponse
import com.example.rp0606.showOffice.ShowOfficeActivity

class ShowTeacherActivity : BaseActivity(),ShowTeacherContract.View {
    lateinit var recyclerView: RecyclerView
    lateinit var back_img:ImageView
    val myAdapter:ShowTeacherAdapter= ShowTeacherAdapter(this)
    val presenter:ShowTeacherPresenter = ShowTeacherPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_teacher)

        recyclerView = findViewById(R.id.teacher_recyclerView)
        recyclerView.apply {
            adapter=myAdapter
            layoutManager = LinearLayoutManager(context)
        }

        back_img = findViewById(R.id.back_img)
        back_img.setOnClickListener(View.OnClickListener {
            backToShowLessonActivity()
        })
    }

    override fun onResume() {
        super.onResume()
        val showLessonResponse: ShowLessonResponse = intent.extras?.get("LessonDetail") as ShowLessonResponse

        presenter.getTeacherList(showLessonResponse.lessonId)

    }

    override fun setTeacherList(data: ArrayList<ShowTeacherResponse>) {
        myAdapter.setDataList(data)
    }

    override fun getTeacherProcess() {
        showProgressDialog(this,"拿取老師")
    }

    override fun getTeacherFail() {
        dismissProgressDialog()
        showToast(this,"拿取老師失敗")    }

    override fun getTeacherComplete() {
        dismissProgressDialog()
        showToast(this,"拿取老師完成")
    }

    override fun backToShowLessonActivity() {
//        intent.setClass(this,ShowLessonActivity::class.java)
//        startActivity(intent)
        finish()
    }

    override fun geToShowOfficeActivity(position:Int) {
        intent.setClass(this,ShowOfficeActivity::class.java)
        intent.putExtra("teacherName",myAdapter.getDataList().get(position).teacher_name)
        startActivity(intent)
    }
}