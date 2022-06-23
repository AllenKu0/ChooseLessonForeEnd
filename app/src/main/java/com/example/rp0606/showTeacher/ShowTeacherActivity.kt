package com.example.rp0606.showTeacher

import android.R.id
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rp0606.BaseActivity
import com.example.rp0606.R
import com.example.rp0606.showLesson.ShowLessonActivity
import com.example.rp0606.showLesson.ShowLessonResponse
import com.example.rp0606.showOffice.ShowOfficeActivity
import android.R.id.message
import android.widget.EditText


class ShowTeacherActivity : BaseActivity(),ShowTeacherContract.View {
    lateinit var recyclerView: RecyclerView
    lateinit var back_img:ImageView
    lateinit var dial_btn:Button
    lateinit var sendMsg_btn:Button
    lateinit var msg_edt:EditText
    var teacherPhoneNumber:String = ""
    val myAdapter:ShowTeacherAdapter= ShowTeacherAdapter(this)
    val presenter:ShowTeacherPresenter = ShowTeacherPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_teacher)
        //鍵盤影響畫面
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        msg_edt = findViewById(R.id.msg_edt)
        recyclerView = findViewById(R.id.teacher_recyclerView)
        recyclerView.apply {
            adapter=myAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(
                context,LinearLayoutManager.VERTICAL))
        }

        back_img = findViewById(R.id.back_img)
        back_img.setOnClickListener(View.OnClickListener {
            backToShowLessonActivity()
        })
        dial_btn = findViewById(R.id.dial_btn)
        dial_btn.setOnClickListener(View.OnClickListener {
            val number:String = "tel:"+teacherPhoneNumber
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(number)))
        })
        sendMsg_btn = findViewById(R.id.sendMsg_btn)
        sendMsg_btn.setOnClickListener(View.OnClickListener {
            val smsIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+teacherPhoneNumber))
            //sms_body 表簡訊內容，改了就讀不到
            smsIntent.putExtra("sms_body", msg_edt.text.toString())
            startActivity(smsIntent)
        })
    }

    override fun onResume() {
        super.onResume()
        val showLessonResponse: ShowLessonResponse = intent.extras?.get("LessonDetail") as ShowLessonResponse

        presenter.getTeacherList(showLessonResponse.lessonId)

    }

    override fun setTeacherList(data: ArrayList<ShowTeacherResponse>) {
        myAdapter.setDataList(data)
        teacherPhoneNumber = data.get(0).teacher_phoneNumber
    }

    override fun getTeacherProcess() {
        showProgressDialog(this,"拿取老師")
    }

    override fun getTeacherFail() {
        dismissProgressDialog()
        showToast(this,"拿取老師失敗")    }

    override fun getTeacherComplete() {
        dismissProgressDialog()
//        showToast(this,"拿取老師完成")
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