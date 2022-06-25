package com.example.rp0606.showTeacher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rp0606.R
import com.example.rp0606.showLesson.ShowLessonActivity
import com.example.rp0606.showLesson.ShowLessonContract

class ShowTeacherAdapter(val view:ShowTeacherActivity): RecyclerView.Adapter<ShowTeacherAdapter.ShowTeacherViewHolder>(),ShowTeacherContract.Adapter {
    var mDataList:ArrayList<ShowTeacherResponse> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowTeacherViewHolder {
        return ShowTeacherViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.show_teacher_recycler_list,parent,false))
    }

    override fun onBindViewHolder(holder:ShowTeacherViewHolder, position: Int) {
        holder.bind(mDataList,position)
        holder.detail_btn.setOnClickListener(View.OnClickListener {
//             view.geToShowOfficeActivity(position)
            view.getOfficeDetail(mDataList.get(position).teacher_name)
        })
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun setDataList(data: ArrayList<ShowTeacherResponse>) {
        mDataList = data
        notifyDataSetChanged()
    }

    override fun getDataList(): ArrayList<ShowTeacherResponse> {
        return mDataList
    }

    class ShowTeacherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),ShowTeacherContract.Adapter.ViewHolder{
        val teacherId: TextView = itemView.findViewById(R.id.teacherId_txt)
        val teacherName: TextView = itemView.findViewById(R.id.teacherName_txt)
        val teacherPhoneNumber: TextView = itemView.findViewById(R.id.teacherPhoneNumber_txt)
        val detail_btn: Button = itemView.findViewById(R.id.detail_btn)
        override fun bind(data: ArrayList<ShowTeacherResponse>, position: Int) {
            teacherId.text = data.get(position).teacher_id.toString()
            teacherName.text = data.get(position).teacher_name
            teacherPhoneNumber.text = data.get(position).teacher_phoneNumber
            if(data.get(position).teacher_name.equals("待聘")){
                detail_btn.isClickable = false
                detail_btn.isEnabled = false
            }
        }
    }
}