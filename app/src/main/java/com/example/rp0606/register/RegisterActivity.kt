package com.example.rp0606.register

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.rp0606.BaseActivity
import com.example.rp0606.MainApplication
import com.example.rp0606.R

class RegisterActivity : BaseActivity(),RegisterContract.View {
    lateinit var account_edt:EditText
    lateinit var password_edt:EditText
    lateinit var passwordAgain_edt:EditText
    lateinit var name_edt:EditText
    lateinit var register_btn:Button
    lateinit var back_img:ImageView
    lateinit var onSureClickListener: DialogInterface.OnClickListener
    val presenter : RegisterPresenter = RegisterPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        account_edt = findViewById(R.id.account_edt)
        password_edt = findViewById(R.id.password_edt)
        name_edt = findViewById(R.id.name_edt)
        passwordAgain_edt = findViewById(R.id.passwordAgain_edt)

        register_btn = findViewById(R.id.register_btn)
        back_img = findViewById(R.id.back_img)

        register_btn.setOnClickListener(View.OnClickListener {
            if (getNetWorkState(MainApplication.applicationContext()) == -1) {
                showDialog("請開啟網路")
            } else{
                if(account_edt.text.toString() != ""){
                    if(password_edt.text.toString().equals(passwordAgain_edt.text.toString())){
                        presenter.register(RegisterRequest(name_edt.text.toString(),account_edt.text.toString(),password_edt.text.toString()))
                    }else{
                        showDialog("兩次密碼需一致")
                    }
                }else{
                    showDialog("帳號不得為空")
                }
            }
        })

        back_img.setOnClickListener(View.OnClickListener {
            geToLoginActivity()
        })

        onSureClickListener = object: DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                geToLoginActivity()
            }
        }
    }

    override fun registerProcess() {
        showProgressDialog(this,"註冊中")
    }

    override fun registerFail() {
        showToast(this,"註冊失敗")
        dismissProgressDialog()
    }

    override fun registerComplete() {
        showOnSureDialog(onSureClickListener,"註冊成功")
    }

    override fun geToLoginActivity() {
        finish()
    }
}