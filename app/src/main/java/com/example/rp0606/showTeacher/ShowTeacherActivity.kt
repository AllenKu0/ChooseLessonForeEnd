package com.example.rp0606.showTeacher

import android.R.id
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rp0606.BaseActivity
import com.example.rp0606.R
import com.example.rp0606.showLesson.ShowLessonActivity
import com.example.rp0606.showLesson.ShowLessonResponse
import com.example.rp0606.showOffice.ShowOfficeActivity
import android.R.id.message
import android.content.DialogInterface
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.example.rp0606.MainApplication
import com.example.rp0606.showOffice.ShowOfficeResponse


class ShowTeacherActivity : BaseActivity(), ShowTeacherContract.View {
    lateinit var recyclerView: RecyclerView
    lateinit var back_img: ImageView
    lateinit var dial_btn: Button
    lateinit var sendMsg_btn: Button
    lateinit var msg_edt: EditText

    lateinit var toolbar: Toolbar
    var teacherPhoneNumber: String = ""
    val myAdapter: ShowTeacherAdapter = ShowTeacherAdapter(this)
    val presenter: ShowTeacherPresenter = ShowTeacherPresenter(this)

    lateinit var builder: AlertDialog.Builder
    lateinit var officeDialog: AlertDialog
    lateinit var onClickListener: DialogInterface.OnClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_teacher)
        //鍵盤影響畫面
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        toolbar = findViewById(R.id.showTeacher_toolbar)

        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        msg_edt = findViewById(R.id.msg_edt)
        recyclerView = findViewById(R.id.teacher_recyclerView)
        recyclerView.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context, LinearLayoutManager.VERTICAL
                )
            )
        }

        back_img = findViewById(R.id.back_img)
        back_img.setOnClickListener(View.OnClickListener {
            backToShowLessonActivity()
        })
        dial_btn = findViewById(R.id.dial_btn)
        dial_btn.setOnClickListener(View.OnClickListener {
            val number: String = "tel:" + teacherPhoneNumber
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(number)))
        })
        sendMsg_btn = findViewById(R.id.sendMsg_btn)
        sendMsg_btn.setOnClickListener(View.OnClickListener {
            val smsIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + teacherPhoneNumber))
            //sms_body 表簡訊內容，改了就讀不到
            smsIntent.putExtra("sms_body", msg_edt.text.toString())
            startActivity(smsIntent)
        })
        // 沒網路強制返回
        onClickListener = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val showLessonResponse: ShowLessonResponse =
            intent.extras?.get("LessonDetail") as ShowLessonResponse
        if (getNetWorkState(MainApplication.applicationContext()) == -1) {
            showOnSureDialog(onClickListener, "請開啟網路")
        } else {
            presenter.getTeacherList(showLessonResponse.lessonId)
        }
    }


    override fun setTeacherList(data: ArrayList<ShowTeacherResponse>) {
        myAdapter.setDataList(data)
        teacherPhoneNumber = data.get(0).teacher_phoneNumber
    }

    override fun getTeacherProcess() {
        showProgressDialog(this, "拿取老師")
    }

    override fun getTeacherFail() {
        dismissProgressDialog()
        showToast(this, "拿取老師失敗")
    }

    override fun getTeacherComplete() {
        dismissProgressDialog()
//        showToast(this,"拿取老師完成")
    }

    override fun backToShowLessonActivity() {
//        intent.setClass(this,ShowLessonActivity::class.java)
//        startActivity(intent)
        finish()
    }

    override fun geToShowOfficeActivity(position: Int) {
        intent.setClass(this, ShowOfficeActivity::class.java)
        intent.putExtra("teacherName", myAdapter.getDataList().get(position).teacher_name)
        startActivity(intent)
    }

    override fun getOfficeProcess() {
        showProgressDialog(this, "拿取辦公室")
    }

    override fun getOfficeFail() {
        dismissProgressDialog()
        showToast(this, "拿取辦公室失敗")
    }

    override fun getOfficeComplete() {
        dismissProgressDialog()
    }

    override fun getOfficeDetail(teacherName:String) {
        presenter.getOfficeList(teacherName)
    }
    override fun showOfficeDetail(t: ShowOfficeResponse?) {

        val inflater = LayoutInflater.from(MainApplication.applicationContext())
        val view = inflater.inflate(R.layout.office_detail, null)
        val cancel_btn = view.findViewById<Button>(R.id.cancel_btn)
        val officeName_txt = view.findViewById<TextView>(R.id.officeName_txt)
        val officePhoneNumber_txt = view.findViewById<TextView>(R.id.officePhoneNumber_txt)

        officeName_txt.setText(t?.officeName)
        officePhoneNumber_txt.setText(t?.officePhoneNumber)

        builder = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(false)
        officeDialog = builder.create()
        officeDialog.show()

        cancel_btn.setOnClickListener(View.OnClickListener {
            officeDialog.dismiss()
        })
    }
}