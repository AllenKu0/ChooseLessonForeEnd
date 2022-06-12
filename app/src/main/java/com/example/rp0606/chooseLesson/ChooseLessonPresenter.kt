package com.example.rp0606.chooseLesson

import android.util.Log
import com.example.rp0606.api.ApiBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.collections.ArrayList

class ChooseLessonPresenter(val view: ChooseLessonContract.View) : ChooseLessonContract.Presenter {
    override fun getNotSelectLesson(account:String) {
        Log.e("ChooseLessonPresenter", "getAllLesson: $account" )
        ApiBuilder.getInstance().getAPI()?.getNotSelectLesson(account)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<List<ChooseLessonResponse>> {
                override fun onSubscribe(d: Disposable?) {
                    Log.e("ChooseLessonPresenter", "onSubscribe: ")
                    view.getLessonProcess()
                }

                override fun onNext(t: List<ChooseLessonResponse>?) {
                    Log.e("ChooseLessonPresenter", "onNext: ")

                    view.setAllLessonList(t as ArrayList<ChooseLessonResponse>)
                }

                override fun onError(e: Throwable?) {
                    Log.e("ChooseLessonPresenter", "onError: " + e.toString())

                    view.getLessonError()
                }

                override fun onComplete() {
                    Log.e("ChooseLessonPresenter", "onComplete: ")

                    view.getLessonComplete()
                }

            })
    }

    override fun getAllLesson() {
        ApiBuilder.getInstance().getAPI()?.getAllLesson()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<List<ChooseLessonResponse>> {
                override fun onSubscribe(d: Disposable?) {
                    Log.e("ChooseLessonPresenter", "onSubscribe: ")
                    view.getLessonProcess()
                }

                override fun onNext(t: List<ChooseLessonResponse>?) {
                    Log.e("ChooseLessonPresenter", "onNext: ")

                    view.setAllLessonList(t as ArrayList<ChooseLessonResponse>)
                }

                override fun onError(e: Throwable?) {
                    Log.e("ChooseLessonPresenter", "onError: " + e.toString())

                    view.getLessonError()
                }

                override fun onComplete() {
                    Log.e("ChooseLessonPresenter", "onComplete: ")

                    view.getLessonComplete()
                }

            })
    }

    override fun chooseLesson(datas: ArrayList<ChooseLessonRequest>) {
        Observable.fromIterable(datas).subscribe {
            Log.e("ChooseLessonPresenter",
                "chooseLesson: subscribe lesson_id: ${it.lesson_id} + account: ${it.user_name}")
            ApiBuilder.getInstance().getAPI()?.chooseLesson(it)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable?) {
                        Log.e("ChooseLessonPresenter", "onSubscribe: ")
                        view.chooseLessonProcess()
                    }

                    override fun onError(e: Throwable?) {
                        Log.e("ChooseLessonPresenter", "onError: " + e.toString())

                        view.chooseLessonError()
                    }

                    override fun onComplete() {
                        Log.e("ChooseLessonPresenter", "onComplete: ")

                        view.chooseLessonComplete()
                    }

                })
        }
    }
}


