package com.example.rp0606.showTeacher

import android.content.Context
import android.util.Log
import com.example.rp0606.api.ApiBuilder
import com.example.rp0606.showOffice.ShowOfficeResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ShowTeacherPresenter(val view:ShowTeacherContract.View) :ShowTeacherContract.Presenter{
    override fun getTeacherList(lessonId:Long) {
        ApiBuilder.getInstance().getAPI()?.getTeacherByLesson(lessonId)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<List<ShowTeacherResponse>>{
                override fun onSubscribe(d: Disposable?) {
                    view.getTeacherProcess()
                }

                override fun onNext(t: List<ShowTeacherResponse>?) {
                    view.setTeacherList(t as ArrayList<ShowTeacherResponse>)
                }

                override fun onError(e: Throwable?) {
                    view.getTeacherFail()
                }

                override fun onComplete() {
                    view.getTeacherComplete()
                }

            })
    }

    override fun getOfficeList(teacherName: String) {

            Log.e("getOfficeList", "getOfficeList: $teacherName" )
            ApiBuilder.getInstance().getAPI()?.getOfficeByTeacher(teacherName)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object :Observer<ShowOfficeResponse>{
                    override fun onSubscribe(d: Disposable?) {
                        view.getOfficeProcess()
                    }

                    override fun onNext(t: ShowOfficeResponse?) {
                        view.showOfficeDetail(t)
                    }

                    override fun onError(e: Throwable?) {
                        Log.e("getOfficeList", "onError: $e" )
                        view.getOfficeFail()
                    }

                    override fun onComplete() {
                        view.getOfficeComplete()
                    }

                })

    }


}