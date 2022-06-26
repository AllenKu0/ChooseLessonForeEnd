package com.example.rp0606.showProfile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.rp0606.BaseActivity
import com.example.rp0606.LoginPreference
import com.example.rp0606.MainApplication
import com.example.rp0606.R

class ShowProfileActivity : BaseActivity(),ShowProfileContract.View {
    val presenter:ShowProfileContract.Presenter = ShowProfilePresenter(this)
    val loginPreference = LoginPreference(MainApplication.applicationContext())
    var account:String = ""
    var passwordVisible:Boolean = false


    lateinit var name_edt : EditText
    lateinit var account_edt : EditText
    lateinit var password_edt : EditText
    lateinit var email_edt : EditText
    lateinit var phone_edt : EditText

    lateinit var back_img:ImageView
    lateinit var visible_img:ImageView

    lateinit var amend_btn: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_profile)

        name_edt = findViewById(R.id.name_edt)
        account_edt = findViewById(R.id.account_edt)
        password_edt = findViewById(R.id.password_edt)
        email_edt = findViewById(R.id.email_edt)
        phone_edt = findViewById(R.id.phone_edt)

        back_img = findViewById(R.id.back_img)
        visible_img = findViewById(R.id.visible_img)

        amend_btn = findViewById(R.id.amend_btn)

        back_img.setOnClickListener(View.OnClickListener {
            finish()
        })

        visible_img.setOnClickListener(View.OnClickListener {
            if(passwordVisible){
                // 密碼不可見
                passwordVisible = !passwordVisible
                visible_img.setImageResource(R.drawable.ic_baseline_visibility_off_24)
                password_edt.transformationMethod = PasswordTransformationMethod.getInstance()
            }else{
                // 密碼可見
                passwordVisible = !passwordVisible
                visible_img.setImageResource(R.drawable.ic_baseline_visibility_24)
                password_edt.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }

        })

        amend_btn.setOnClickListener(View.OnClickListener {
            val showProfileRequest = ShowProfileRequest(name_edt.text.toString()
                ,email_edt.text.toString(),phone_edt.text.toString())
            presenter.upDateProfile(account,showProfileRequest)
        })
        account = loginPreference.getAccount()
    }

    override fun onResume() {
        super.onResume()
        presenter.getProfile(account)
    }
    override fun setProfile(data: ShowProfileResponse?) {
        name_edt.setText(data?.name)
        account_edt.setText(data?.account)
        password_edt.setText(data?.password)
        email_edt.setText(data?.email)
        Log.e("電話", "setProfile: "+data?.student_phone )
        phone_edt.setText(data?.student_phone)

    }

    override fun onErrorSolution(msg:String) {
        showToast(this,msg)
        dismissProgressDialog()
    }

    override fun onCompleteSolution() {
        dismissProgressDialog()
    }

    override fun onPrecessSolution(msg:String) {
        showProgressDialog(this,msg)
    }

    override fun updateComplete() {
        dismissProgressDialog()
        showToast(MainApplication.applicationContext(),"修改成功")
        finish()
    }
}