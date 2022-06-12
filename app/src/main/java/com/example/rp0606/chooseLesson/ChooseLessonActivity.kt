package com.example.rp0606.chooseLesson

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rp0606.BaseActivity
import com.example.rp0606.LoginPreference
import com.example.rp0606.MainApplication
import com.example.rp0606.R

class ChooseLessonActivity : BaseActivity(),ChooseLessonContract.View {
    lateinit var recyclerView: RecyclerView
    lateinit var chooseLesson_btn:Button
    lateinit var filter_btn:Button
    lateinit var back_img:ImageView
    val myAdapter : ChooseLessonAdapter = ChooseLessonAdapter()
    val loginPreference:LoginPreference = LoginPreference(MainApplication.applicationContext())
    val presenter :ChooseLessonPresenter = ChooseLessonPresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_lesson2)

        recyclerView = findViewById(R.id.choose_lesson_recyclerView)
        chooseLesson_btn = findViewById(R.id.choose_lesson_btn)
        filter_btn = findViewById(R.id.filter_btn)
        back_img = findViewById(R.id.back_img)

        recyclerView.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(context)
        }


        chooseLesson_btn.setOnClickListener(View.OnClickListener {
            presenter.chooseLesson(myAdapter.getChooseLesson())
        })

        filter_btn.setOnClickListener(View.OnClickListener {
            presenter.getNotSelectLesson(loginPreference.getAccount())
        })

        back_img.setOnClickListener(View.OnClickListener {
            backToShowLessonActivity()
        })
    }

    override fun onResume() {
        super.onResume()
        presenter.getAllLesson()
//        presenter.getNotSelectLesson(loginPreference.getAccount())
    }

    override fun setAllLessonList(data: ArrayList<ChooseLessonResponse>) {
        Log.e("setAllLessonList", "setAllLessonList: "+data.get(0).lessonName)
        myAdapter.setDataList(data)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun getNotSelectProcess() {
        showProgressDialog(this,"過濾衝堂中")
    }

    override fun getNotSelectError() {
        showToast(this,"過濾衝堂失敗")
        dismissProgressDialog()
    }

    override fun getNotSelectComplete() {
        showToast(this,"過濾衝堂完成")
        dismissProgressDialog()    }

    override fun getLessonProcess() {
        showProgressDialog(this,"讀取中")
    }

    override fun getLessonError() {
        showToast(this,"讀取失敗")
        dismissProgressDialog()
    }

    override fun getLessonComplete() {
        showToast(this,"拿取課程完成")
        dismissProgressDialog()
    }

    override fun chooseLessonProcess() {
        showProgressDialog(this,"讀取中")
    }

    override fun chooseLessonError() {
        showToast(this,"選課失敗")
        dismissProgressDialog()
    }

    override fun chooseLessonComplete() {
        showToast(this,"選取課程完成")
        dismissProgressDialog()
    }

    override fun backToShowLessonActivity() {
//        intent.setClass(this,ShowLessonActivity::class.java)
//        startActivity(intent)
        finish()
    }
}