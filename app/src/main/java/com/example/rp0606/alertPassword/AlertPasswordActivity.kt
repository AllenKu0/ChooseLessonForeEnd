package com.example.rp0606.alertPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.rp0606.BaseActivity
import com.example.rp0606.LoginPreference
import com.example.rp0606.MainApplication
import com.example.rp0606.R

class AlertPasswordActivity : BaseActivity(),AlertPasswordContract.View {
    val presenter:AlertPasswordContract.Presenter = AlertPasswordPresenter(this)
    val loginPreference:LoginPreference = LoginPreference(MainApplication.applicationContext())
    lateinit var account_edt:EditText
    lateinit var oldPassword_edt:EditText
    lateinit var newPassword_edt:EditText
    lateinit var newPasswordConfirm_edt:EditText

    lateinit var confirm_btn:Button

    lateinit var back_img:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_password)

        account_edt = findViewById(R.id.account_edt)
        oldPassword_edt = findViewById(R.id.oldPassword_edt)
        newPassword_edt = findViewById(R.id.newPassword_edt)
        newPasswordConfirm_edt = findViewById(R.id.newPasswordConfirm_edt)

        confirm_btn = findViewById(R.id.confirm_btn)
        confirm_btn.setOnClickListener(View.OnClickListener {
            Log.e("TAG", "舊密碼: "+loginPreference.getPassWord() )
            if(!oldPassword_edt.text.toString().equals(loginPreference.getPassWord())){
                showDialog("舊密碼錯誤")
            }else if((newPassword_edt.text.toString().equals(newPasswordConfirm_edt.text.toString()))){
                presenter.alertPassword(loginPreference.getAccount(),newPassword_edt.text.toString())
            }else{
                showDialog("新密碼確認需相同")
            }
        })

        back_img = findViewById(R.id.back_img)
        back_img.setOnClickListener(View.OnClickListener {
            finish()
        })

    }

    override fun onResume() {
        super.onResume()

        account_edt.setText(loginPreference.getAccount())
    }

    override fun alertProcess(msg:String) {
        showProgressDialog(this,msg)
    }

    override fun alertComplete() {
        dismissProgressDialog()
        Log.e("TAG", "新密碼: "+newPassword_edt.text.toString())
        loginPreference.savaPassWord(newPassword_edt.text.toString())
        finish()
    }

    override fun alertFail(msg: String) {
        dismissProgressDialog()
        showToast(this,msg)
    }
}