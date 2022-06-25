package com.example.rp0606.showLesson

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rp0606.*
import com.example.rp0606.chooseLesson.ChooseLessonActivity
import com.example.rp0606.showClassRoom.ShowClassRoomActivity
import com.example.rp0606.showTeacher.ShowTeacherActivity

class ShowLessonActivity : BaseActivity(), ShowLessonContract.View {
    private  val TAG = "ShowLessonActivity"
    lateinit var choose_btn: Button
    lateinit var deleteSelectLesson_btn: Button
    lateinit var recycler_view: RecyclerView
    lateinit var toolbar:androidx.appcompat.widget.Toolbar
    lateinit var credit_txt:TextView
    val context: Context = this
    lateinit var presenter: ShowLessonContract.Presenter

    val loginPreference: LoginPreference = LoginPreference(MainApplication.applicationContext())
    val myAdapter: ShowLessonAdapter = ShowLessonAdapter(this)
    val account:String = loginPreference.getAccount()

    lateinit var onSureClickListener: DialogInterface.OnClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_lesson)

        choose_btn = findViewById(R.id.to_choose_lesson_btn)
        deleteSelectLesson_btn = findViewById(R.id.deleteSelectLesson_btn)
        // 學分
        credit_txt = findViewById(R.id.credit_txt)

        toolbar = findViewById(R.id.showLesson_toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        presenter = ShowLessonPresenter(this)

        recycler_view = findViewById(R.id.recyclerView)
        recycler_view.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration
                    (context, LinearLayoutManager.VERTICAL)
            )
        }

        choose_btn.setOnClickListener(View.OnClickListener {
            //選擇
            var intent = Intent(this, ChooseLessonActivity::class.java)
            startActivity(intent)
        })

        deleteSelectLesson_btn.setOnClickListener(View.OnClickListener {
            if(myAdapter.getChooseLesson().size > 0){
                //退選
                showChooseDialog(onSureClickListener,"確定要退選嗎")
            }else{
                showToast(this,"請至少選擇一堂課程")
            }
        })

        onSureClickListener = object:DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if (getNetWorkState(MainApplication.applicationContext()) == -1) {
                    showDialog("請開啟網路")
                } else{
                    presenter.dropOutLesson(account,myAdapter.getChooseLesson())
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        Log.e("ShowLessonActivity", "onCreate account: $account")
        //未連線顯示通知
        if (getNetWorkState(MainApplication.applicationContext()) == -1) {
            showDialog("請開啟網路")
        } else{
            //拿選課列表
            presenter.getLessonList(account)
        }
    }

    override fun setLessonList(data: ArrayList<ShowLessonResponse>) {
        //設定列表
        myAdapter.setDataList(data)
    }

    override fun onProcess(msg: String) {
        showProgressDialog(this, msg)
    }

    override fun onFail(msg: String) {
        dismissProgressDialog()
        showToast(this, msg)
    }

    override fun onComplete(msg: String) {
        dismissProgressDialog()
        credit_txt.setText(myAdapter.getCreditCount().toString());

        if(myAdapter.getCreditCount() > 24){
            showDialog("已超出選修學分，請進行退選")
        }
//        showToast(this, msg)
    }

    override fun dropOutLessonComplete(msg: String) {
        dismissProgressDialog()
        showToast(this, msg)
        presenter.getLessonList(account)
    }

    //查看教室資訊
    override fun goToShowClassRoomActivity(data: ShowLessonResponse) {
        val intent: Intent = Intent(context, ShowClassRoomActivity::class.java)
        intent.putExtra("LessonDetail", data)
        startActivity(intent)
    }

    override fun logout() {
        Log.e(TAG, "logout: " )
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.login_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            //登出
            R.id.menu_logout -> logout()
            //重載
            R.id.menu_reload ->  {
                if (getNetWorkState(MainApplication.applicationContext()) == -1) {
                    showDialog("請開啟網路")
                } else{
                    presenter.getLessonList(account)
                }
            }
            else -> Log.e(TAG, "onOptionsItemSelected: "+item.itemId)
        }
        return super.onOptionsItemSelected(item)
    }

    //返回鍵監聽
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            showToast(context,"請按右上角登出")
            return false
        }
        return super.onKeyDown(keyCode, event)
    }
}