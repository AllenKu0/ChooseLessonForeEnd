package com.example.rp0606.showLesson

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rp0606.*
import com.example.rp0606.chooseLesson.ChooseLessonActivity
import com.example.rp0606.showTeacher.ShowTeacherActivity

class ShowLessonActivity : BaseActivity(),ShowLessonContract.View {
    lateinit var choose_btn:Button
    lateinit var recycler_view: RecyclerView
    val context:Context =this
    lateinit var presenter: ShowLessonContract.Presenter

    val loginPreference:LoginPreference = LoginPreference(MainApplication.applicationContext())

    val myAdapter:ShowLessonAdapter = ShowLessonAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_lesson)

//        Log.e("ShowLessonActivity", "onCreate account: $account")
        choose_btn = findViewById(R.id.to_choose_lesson_btn)
        presenter = ShowLessonPresenter(this)

        //拿選課列表
//        presenter.getLessonList(account)

        recycler_view = findViewById(R.id.recyclerView)
        recycler_view.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(context)
        }

        choose_btn.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, ChooseLessonActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onResume() {
        super.onResume()
        val account:String = loginPreference.getAccount()
        Log.e("ShowLessonActivity", "onCreate account: $account")
        //拿選課列表
        presenter.getLessonList(account)
    }

    override fun setLessonList(data:ArrayList<ShowLessonResponse>) {
        myAdapter.setDataList(data)
        recycler_view.adapter?.notifyDataSetChanged()
    }

    override fun getLessonProcess() {
        showProgressDialog(this,"拿取選課")
    }

    override fun getLessonFail() {
        dismissProgressDialog()
        showToast(this,"拿取選課失敗")
    }

    override fun getLessonComplete() {
        dismissProgressDialog()
        showToast(this,"拿取選課完成")
    }

    override fun goToShowTeacherActivity(data:ShowLessonResponse) {
        val intent:Intent = Intent(context, ShowTeacherActivity::class.java)
        intent.putExtra("LessonDetail",data)
        startActivity(intent)
    }
}