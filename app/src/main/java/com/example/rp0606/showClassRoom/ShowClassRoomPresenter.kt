package com.example.rp0606.showClassRoom

import android.util.Log
import com.example.rp0606.api.ApiBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ShowClassRoomPresenter(val view: ShowClassRoomContract.View) :
    ShowClassRoomContract.Presenter {
    override fun getClassRoom(lessonName: String) {
        Log.e("getClassRoom", "getClassRoom: $lessonName" )
        ApiBuilder.getInstance().getAPI()?.getClassRoomByLesson(lessonName)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<ShowClassRoomResponse> {
                override fun onSubscribe(d: Disposable?) {
                    view.getClassRoomProcess()
                }

                override fun onNext(t: ShowClassRoomResponse?) {
                    view.setClassRoom(t)
                }

                override fun onError(e: Throwable?) {
                    Log.e("getClassRoom", "onError: $e")
                    view.getClassRoomFail()
                }

                override fun onComplete() {
                    view.getClassRoomComplete()
                }
            })
    }
}