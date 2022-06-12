package com.example.rp0606.showLesson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rp0606.R

class ShowLessonAdapter (val view:ShowLessonActivity): RecyclerView.Adapter<ShowLessonAdapter.ShowLessonViewHolder>(),ShowLessonContract.Adapter{
    var lessonList:ArrayList<ShowLessonResponse> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowLessonViewHolder {
        return ShowLessonViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.show_lesson_recycler_list,parent,false))
    }

    override fun onBindViewHolder(holder: ShowLessonViewHolder, position: Int) {
        holder.bind(lessonList,position,view)
    }

    override fun getItemCount(): Int {
        return lessonList.size
    }

    override fun setDataList(data:ArrayList<ShowLessonResponse>){
        lessonList = data
    }

    class ShowLessonViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView),ShowLessonContract.Adapter.ViewHolder{
        val lessonId:TextView = itemView.findViewById(R.id.lessonId_txt)
        val lessonName:TextView = itemView.findViewById(R.id.lessonName_txt)
        val lessonCredit:TextView = itemView.findViewById(R.id.lessonCredit_txt)
        val lessonStatus :TextView = itemView.findViewById(R.id.lessonStatus_txt)
        val detail_btn:Button = itemView.findViewById(R.id.detail_btn)

        override fun bind(data:ArrayList<ShowLessonResponse>, position: Int,view: ShowLessonActivity){
            lessonId.text = data.get(position).lessonId.toString()
            lessonName.text = data.get(position).lessonName
            lessonCredit.text = data.get(position).lessonCredit.toString()
            lessonStatus.text = data.get(position).lessonStatus
            detail_btn.setOnClickListener(View.OnClickListener {
                view.goToShowClassRoomActivity(data.get(position))
            })
        }
    }
}