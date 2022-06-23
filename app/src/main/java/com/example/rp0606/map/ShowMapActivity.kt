package com.example.rp0606.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.rp0606.R

class ShowMapActivity : AppCompatActivity() {
    lateinit var one_txt:TextView
    lateinit var two_txt:TextView
    lateinit var three_txt:TextView
    lateinit var four_txt:TextView
    lateinit var five_txt:TextView
    lateinit var six_txt:TextView
    lateinit var seven_txt:TextView
    lateinit var eight_txt:TextView

    lateinit var back_img: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_map)

        one_txt = findViewById(R.id.one_txt)
        two_txt = findViewById(R.id.two_txt)
        three_txt = findViewById(R.id.three_txt)
        four_txt = findViewById(R.id.four_txt)
        five_txt = findViewById(R.id.five_txt)
        six_txt = findViewById(R.id.six_txt)
        seven_txt = findViewById(R.id.seven_txt)
        eight_txt = findViewById(R.id.eight_txt)

        back_img = findViewById(R.id.back_img)

        back_img.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    override fun onResume() {
        super.onResume()
        val className:String = intent.extras?.get("className") as String

        initColor()
        checkClassRoom(className)

    }

    fun initColor() {
        one_txt.setTextColor(ContextCompat.getColor(this, R.color.black))
        two_txt.setTextColor(ContextCompat.getColor(this, R.color.black))
        three_txt.setTextColor(ContextCompat.getColor(this, R.color.black))
        four_txt.setTextColor(ContextCompat.getColor(this, R.color.black))
        five_txt.setTextColor(ContextCompat.getColor(this, R.color.black))
        six_txt.setTextColor(ContextCompat.getColor(this, R.color.black))
        seven_txt.setTextColor(ContextCompat.getColor(this, R.color.black))
        eight_txt.setTextColor(ContextCompat.getColor(this, R.color.black))
    }

    fun checkClassRoom(className:String){
        when(className[0]){
            '1' -> one_txt.setTextColor(ContextCompat.getColor(this, R.color.high_light))
            '2' -> two_txt.setTextColor(ContextCompat.getColor(this, R.color.high_light))
            '3' -> three_txt.setTextColor(ContextCompat.getColor(this, R.color.high_light))
            '4' -> four_txt.setTextColor(ContextCompat.getColor(this, R.color.high_light))
            '5' -> five_txt.setTextColor(ContextCompat.getColor(this, R.color.high_light))
            '6' -> six_txt.setTextColor(ContextCompat.getColor(this, R.color.high_light))
            '7' -> seven_txt.setTextColor(ContextCompat.getColor(this, R.color.high_light))
            '8' -> eight_txt.setTextColor(ContextCompat.getColor(this, R.color.high_light))
        }
    }
}