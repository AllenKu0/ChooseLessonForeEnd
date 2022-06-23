package com.example.rp0606.showOffice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.rp0606.BaseActivity
import com.example.rp0606.MainApplication
import com.example.rp0606.R

class ShowOfficeActivity : BaseActivity(), ShowOfficeContract.View {
    val presenter: ShowOfficePresenter = ShowOfficePresenter(this)
    var teacherName = ""
    lateinit var back_img: ImageView
    lateinit var office_txt: TextView
    lateinit var officePhoneNumber_txt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_office)

        office_txt = findViewById(R.id.office_txt)
        officePhoneNumber_txt = findViewById(R.id.officePhoneNumber_txt)

        back_img = findViewById(R.id.back_img)
        back_img.setOnClickListener(View.OnClickListener {
            backToShowTeacherActivity()
        })
        teacherName = intent.extras?.get("teacherName") as String
    }

    override fun onResume() {
        super.onResume()
        if (getNetWorkState(MainApplication.applicationContext()) == -1) {
            showDialog("請開啟網路")
        } else {
            presenter.getOfficeList(teacherName)
        }

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
//        showToast(this,"拿取辦公室完成")
    }

    override fun setOfficeList(data: ShowOfficeResponse?) {
        office_txt.text = data?.officeName
        officePhoneNumber_txt.text = data?.officePhoneNumber
    }

    override fun backToShowTeacherActivity() {
        finish()
    }
}