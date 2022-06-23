package com.example.rp0606.chooseLesson

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rp0606.BaseActivity
import com.example.rp0606.LoginPreference
import com.example.rp0606.MainApplication
import com.example.rp0606.R

class ChooseLessonActivity : BaseActivity(), ChooseLessonContract.View {
    private val TAG = ChooseLessonActivity::class.java.name
    lateinit var recyclerView: RecyclerView
    lateinit var chooseLesson_btn: Button
    lateinit var filter_btn: Button
    lateinit var back_img: ImageView

    //提示dialog
    lateinit var builder: AlertDialog.Builder
    lateinit var hintDialog: AlertDialog

    lateinit var toolbar:Toolbar
    val myAdapter: ChooseLessonAdapter = ChooseLessonAdapter()
    val loginPreference: LoginPreference = LoginPreference(MainApplication.applicationContext())
    val presenter: ChooseLessonPresenter = ChooseLessonPresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_lesson2)

        toolbar = findViewById(R.id.chooseLesson_toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        recyclerView = findViewById(R.id.choose_lesson_recyclerView)
        chooseLesson_btn = findViewById(R.id.choose_lesson_btn)
        filter_btn = findViewById(R.id.filter_btn)
        back_img = findViewById(R.id.back_img)

        recyclerView.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration
                    (context, LinearLayoutManager.VERTICAL)
            )
        }

        //選課按鈕
        chooseLesson_btn.setOnClickListener(View.OnClickListener {
            if (getNetWorkState(MainApplication.applicationContext()) == -1) {
                showDialog("請開啟網路")
            } else {
                if (myAdapter.getChooseLesson().size > 0) {
                    presenter.chooseLesson(myAdapter.getChooseLesson())
                } else {
                    showToast(this, "請選擇至少一堂課程")
                }
            }
        })
        // 過濾已選課程
        filter_btn.setOnClickListener(View.OnClickListener {
            if (getNetWorkState(MainApplication.applicationContext()) == -1) {
                showDialog("請開啟網路")
            } else{
                presenter.getNotSelectLesson(loginPreference.getAccount())
                //關閉過濾
//                filter_btn.isClickable = false
//                filter_btn.isEnabled = false
            }
        })

        back_img.setOnClickListener(View.OnClickListener {
            backToShowLessonActivity()
        })
    }

    override fun onResume() {
        super.onResume()
        //開啟過濾
//        filter_btn.isClickable = true
//        filter_btn.isEnabled = true

        //未連線顯示通知
        if (getNetWorkState(MainApplication.applicationContext()) == -1) {
            showDialog("請開啟網路")
        } else {
            presenter.getAllLesson()
        }
        if (!loginPreference.getIsHintDialogShow()) {
            //@LayoutRes 測試
            showHintDialog(R.layout.choose_lesson_alertdialog)
        }
//        presenter.getNotSelectLesson(loginPreference.getAccount())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.base_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_reload -> {
                if (getNetWorkState(MainApplication.applicationContext()) == -1) {
                    showDialog("請開啟網路")
                } else {
                    presenter.getAllLesson()
                }
            }
            else -> Log.e(TAG, "onOptionsItemSelected: ${item.itemId}" )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setAllLessonList(data: ArrayList<ChooseLessonResponse>) {
        myAdapter.setDataList(data)
    }

    override fun getNotSelectProcess() {
        showProgressDialog(this, "過濾衝堂中")
    }

    override fun getNotSelectError() {
        showToast(this, "過濾衝堂失敗")
        dismissProgressDialog()
    }

    override fun getNotSelectComplete() {
        dismissProgressDialog()
    }

    override fun getLessonProcess() {
        showProgressDialog(this, "讀取中")
    }

    override fun getLessonError() {
        showToast(this, "讀取失敗")
        dismissProgressDialog()
    }

    override fun getLessonComplete() {
        dismissProgressDialog()
    }

    override fun chooseLessonProcess() {
        showProgressDialog(this, "讀取中")
    }

    override fun chooseLessonError() {
        showToast(this, "選課失敗")
        dismissProgressDialog()
    }

    override fun chooseLessonComplete() {
        showToast(this, "選取課程完成")
        dismissProgressDialog()
        finish()
    }

    override fun backToShowLessonActivity() {
//        intent.setClass(this,ShowLessonActivity::class.java)
//        startActivity(intent)
        finish()
    }

    override fun showHintDialog(layoutId: Int) {
        val inflater = LayoutInflater.from(MainApplication.applicationContext())
        val view = inflater.inflate(layoutId, null)
        val cancel_btn = view.findViewById<Button>(R.id.cancel_btn)
        val isKnow_chb = view.findViewById<CheckBox>(R.id.isKnow_chb)
        val arrow_img: ImageView = view.findViewById(R.id.arrow_img)
        arrow_img.setImageDrawable(getDrawable(R.drawable.ic_baseline_call_received_24))
        builder = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(false)

        hintDialog = builder.create()

        hintDialog.window?.attributes?.x = 10
        hintDialog.window?.attributes?.y = 150
        hintDialog.show()

        cancel_btn.setOnClickListener(View.OnClickListener {
            hintDialog.dismiss()
            loginPreference.setInHintDialogShow(isKnow_chb.isChecked)
        })

    }
}