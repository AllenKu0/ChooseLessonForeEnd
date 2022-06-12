package com.example.rp0606.showLesson

import android.util.Log
import com.example.rp0606.api.ApiBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ShowLessonPresenter(val view : ShowLessonContract.View):ShowLessonContract.Presenter {
    private val TAG = "ShowLessonPresenter"
    override fun getLessonList(account:String) {
        ApiBuilder.getInstance().getAPI()?.getAllMyLesson(account)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<List<ShowLessonResponse>>{
                override fun onSubscribe(d: Disposable?) {
                    Log.e(TAG, "onSubscribe: ")
                    view.getLessonProcess()
                }

                override fun onNext(t: List<ShowLessonResponse>?) {
                    Log.e(TAG, "onNext: ")
                    view.setLessonList(t as ArrayList<ShowLessonResponse>)
                }

                override fun onError(e: Throwable?) {
                    Log.e(TAG, "onError: "+e.toString())
                    view.getLessonFail()
                }

                override fun onComplete() {
                    Log.e(TAG, "onComplete: ")
                    view.getLessonComplete()
                }
            })

    }
}