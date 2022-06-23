package com.example.rp0606.login

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.example.rp0606.*
import com.example.rp0606.register.RegisterActivity
import com.example.rp0606.service.MyService
import com.example.rp0606.service.MyService.MyBinder
import com.example.rp0606.service.MyServiceConnection
import com.example.rp0606.showLesson.ShowLessonActivity

class LoginActivity : BaseActivity(), LoginContract.View{
    lateinit var login_btn: Button
    lateinit var register_btn: Button
    lateinit var account_edt: EditText
    lateinit var password_edt: EditText
    lateinit var remember_chb: CheckBox

    lateinit var bindIntent:Intent

    val myServiceConnection:MyServiceConnection = MyServiceConnection()

    init {
        Log.e("TAG", "init ")
    }

    //    lateinit var apiService : ApiService
    lateinit var presenter: LoginContract.Presenter
    val loginPreference: LoginPreference = LoginPreference(MainApplication.applicationContext())




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindIntent = Intent(this,MyService::class.java)
        startService(bindIntent)
        bindService(bindIntent,myServiceConnection, Context.BIND_AUTO_CREATE)


        login_btn = findViewById(R.id.login_btn)
        register_btn = findViewById(R.id.register_btn)
        account_edt = findViewById(R.id.account_edt)
        password_edt = findViewById(R.id.password_edt)

        remember_chb = findViewById(R.id.remember_chb)

        if (loginPreference.getRememberStatus()) {
            account_edt.setText(loginPreference.getAccount())
            password_edt.setText(loginPreference.getPassWord())
            remember_chb.isChecked = loginPreference.getRememberStatus()
        }

        presenter = LoginPresenter(this)
        login_btn.setOnClickListener(View.OnClickListener {
            if(getNetWorkState(MainApplication.applicationContext()) == -1){
                showDialog("請開啟網路")
            }else{
                var userLogin = LoginRequest(account_edt.text.toString(), password_edt.text.toString())
                Log.e(
                    "TAG",
                    "帳號: " + account_edt.text.toString() + " 密碼: " + password_edt.text.toString()
                )
                presenter.loginApi(userLogin)
            }
        })

        register_btn.setOnClickListener(View.OnClickListener {
            var intent: Intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        })

    }

    override fun loginSuccess() {
        showToast(this, "登入成功")
        loginPreference.saveAccount(account_edt.text.toString())
        loginPreference.savaPassWord(password_edt.text.toString())
        loginPreference.saveRememberStatus(remember_chb.isChecked)
        Log.e("TAG", "loginSuccess: " + loginPreference.getAccount())
        var intent: Intent = Intent(this, ShowLessonActivity::class.java)
        startActivity(intent)
        dismissProgressDialog()
    }

    override fun loginError() {
        showToast(this, "登入失敗")
        dismissProgressDialog()
    }

    override fun loginProcess() {
        showProgressDialog(this, "登入中")
    }
}