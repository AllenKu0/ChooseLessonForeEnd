package com.example.rp0606.chooseLesson

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rp0606.LoginPreference
import com.example.rp0606.MainApplication
import com.example.rp0606.R
import com.example.rp0606.showLesson.ShowLessonAdapter
import java.util.zip.Inflater

class ChooseLessonAdapter: RecyclerView.Adapter<ChooseLessonAdapter.ChooseLessonViewHolder>() ,ChooseLessonContract.Adapter{
    var mDataList:ArrayList<ChooseLessonList> = ArrayList()
    val loginPreference:LoginPreference = LoginPreference(MainApplication.applicationContext())
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseLessonViewHolder {
        return ChooseLessonViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.show_all_lesson_recycler_list,parent,false))
    }

    override fun onBindViewHolder(holder: ChooseLessonViewHolder, position: Int) {
        holder.bind(mDataList,position)
        holder.choose_cbx.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.e("CheckLesson", "onBindViewHolder: position: $position isChecked: $isChecked" )
            if(position < mDataList.size) {
                mDataList.get(position).isCheck = isChecked
//            notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    fun setDataList(datas:ArrayList<ChooseLessonResponse>){
        var dataList:ArrayList<ChooseLessonList> = ArrayList()
        for (i in 0 until datas.size){
            val data:ChooseLessonList = ChooseLessonList(datas.get(i).lessonId
                ,datas.get(i).lessonName,datas.get(i).lessonCredit,datas.get(i).lessonStatus,false)
            dataList.add(data)
        }
        //升序
        dataList.sortBy {it.lessonId}

        this.mDataList = dataList
        notifyDataSetChanged()
    }

    class ChooseLessonViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val lessonId: TextView = itemView.findViewById(R.id.lessonId_txt)
        val lessonName: TextView = itemView.findViewById(R.id.lessonName_txt)
        val lessonCredit: TextView = itemView.findViewById(R.id.lessonCredit_txt)
        val lessonStatus:TextView = itemView.findViewById(R.id.lessonStatus_txt)
        val choose_cbx:CheckBox = itemView.findViewById(R.id.choose_chb)

        fun bind(data:List<ChooseLessonList>,position:Int){
            lessonId.text = data.get(position).lessonId.toString()
            lessonName.text = data.get(position).lessonName
            lessonCredit.text = data.get(position).lessonCredit.toString()
            lessonStatus.text = data.get(position).lessonStatus
            choose_cbx.isChecked = data.get(position).isCheck
//            choose_cbx.setOnCheckedChangeListener { buttonView, isChecked ->
//                data.get(position).isCheck
//            }
        }
    }

    override fun getChooseLesson() :ArrayList<ChooseLessonRequest>{
        var dataList:ArrayList<ChooseLessonRequest> = ArrayList()
        for(i in 0 until mDataList.size){
            if(mDataList.get(i).isCheck){
                val data = ChooseLessonRequest(mDataList.get(i).lessonId,loginPreference.getAccount())
                dataList.add(data)
            }
        }
        return dataList
    }
}