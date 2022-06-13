package com.example.rp0606.showLesson

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rp0606.R
import java.util.*
import kotlin.collections.ArrayList

class ShowLessonAdapter (val view:ShowLessonActivity): RecyclerView.Adapter<ShowLessonAdapter.ShowLessonViewHolder>(),ShowLessonContract.Adapter{
    var lessonList:ArrayList<ShowLessonList> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowLessonViewHolder {
        return ShowLessonViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.show_lesson_recycler_list,parent,false))
    }

    override fun onBindViewHolder(holder: ShowLessonViewHolder, position: Int) {
        holder.bind(lessonList,position,view)
        holder.select_chb.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.e("CheckLesson", "onBindViewHolder: position: $position isChecked: $isChecked size: ${lessonList.size}")
            if(position < lessonList.size){
                lessonList.get(position).isCheck = isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return lessonList.size
    }

    override fun setDataList(datas:ArrayList<ShowLessonResponse>){
        Log.e("setDataList", "size ${datas.size}")
        var dataList:ArrayList<ShowLessonList> = ArrayList()
        for (i in 0 until datas.size){
            val data: ShowLessonList = ShowLessonList(datas.get(i).lessonId
                ,datas.get(i).lessonName,datas.get(i).lessonCredit,datas.get(i).lessonStatus,false)
            dataList.add(data)
        }
        // 升序
        dataList.sortBy {it.lessonId}
//        // 降序
//        dataList.sortWith (compareByDescending { it.lessonId })
        this.lessonList = dataList
        notifyDataSetChanged()
    }

    override fun getChooseLesson(): ArrayList<ShowLessonList> {
        var datas:ArrayList<ShowLessonList> = ArrayList()
        for(i in 0 until lessonList.size){
            if(lessonList.get(i).isCheck){
                datas.add(lessonList.get(i))
            }
        }
        return datas
    }

    class ShowLessonViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView),ShowLessonContract.Adapter.ViewHolder{
        val lessonId:TextView = itemView.findViewById(R.id.lessonId_txt)
        val lessonName:TextView = itemView.findViewById(R.id.lessonName_txt)
        val lessonCredit:TextView = itemView.findViewById(R.id.lessonCredit_txt)
        val lessonStatus :TextView = itemView.findViewById(R.id.lessonStatus_txt)
        val detail_btn:Button = itemView.findViewById(R.id.detail_btn)
        val select_chb:CheckBox = itemView.findViewById(R.id.select_chb)

        override fun bind(data:ArrayList<ShowLessonList>, position: Int, view: ShowLessonActivity){
            lessonId.text = data.get(position).lessonId.toString()
            lessonName.text = data.get(position).lessonName
            lessonCredit.text = data.get(position).lessonCredit.toString()
            lessonStatus.text = data.get(position).lessonStatus
            select_chb.isChecked = data.get(position).isCheck
            detail_btn.setOnClickListener(View.OnClickListener {
                val checkData:ShowLessonResponse = ShowLessonResponse(data.get(position).lessonId
                    ,data.get(position).lessonName,data.get(position).lessonCredit,data.get(position).lessonStatus)
                view.goToShowClassRoomActivity(checkData)
            })
        }
    }
}