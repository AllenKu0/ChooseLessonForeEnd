package com.example.rp0606.showLesson

import android.util.Log
import com.example.rp0606.api.ApiBuilder
import com.example.rp0606.showProfile.ShowProfileResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.core.Observable
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
                    Log.e(TAG, "onSubscribe: getLessonList")
                    view.onProcess("取得課程中")
                }

                override fun onNext(t: List<ShowLessonResponse>?) {
                    Log.e(TAG, "onNext: getLessonList")
                    view.setLessonList(t as ArrayList<ShowLessonResponse>)
                }

                override fun onError(e: Throwable?) {
                    Log.e(TAG, "onError: getLessonList "+e.toString())
                    view.onFail("取得課程失敗")
                }

                override fun onComplete() {
                    Log.e(TAG, "onComplete: ")
                    view.getLessonComplete("取得課程完成")
                }
            })

    }

    override fun dropOutLesson(account: String, lessonList: List<ShowLessonList>) {
        var isSubscribe:Boolean = false
        var completeTimes:Int =0
         Observable.fromIterable(lessonList).subscribe{
             ApiBuilder.getInstance().getAPI()?.dropOutLesson(DropLessonRequest(it.lessonId,account))
                 ?.subscribeOn(Schedulers.io())
                 ?.observeOn(AndroidSchedulers.mainThread())
                 ?.subscribe(object : CompletableObserver{
                     override fun onSubscribe(d: Disposable?) {
                         Log.e(TAG, "onSubscribe:dropOutLesson " )
                         if(!isSubscribe){
                             Log.e(TAG, "onSubscribe:times: isSubscribe $isSubscribe")
                             view.onProcess("退選中")
                             isSubscribe = true
                         }
                     }

                     override fun onComplete() {
                         Log.e(TAG, "onComplete:dropOutLesson ")
                         completeTimes++
                         if(completeTimes == lessonList.size){
                             Log.e(TAG, "onComplete:completeTimes: $completeTimes")
                             view.dropOutLessonComplete("退選完成")
                         }
                     }

                     override fun onError(e: Throwable?) {
                         Log.e(TAG, "onError: $e")
                         view.onFail("退選失敗")
                     }
                 })
         }
    }

    override fun getStudentProfile(account: String) {
        ApiBuilder.getInstance().getAPI()?.getStudentProfile(account)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<ShowProfileResponse> {
                override fun onSubscribe(d: Disposable?) {
                    view.onProcess("拿取個人資訊")
                }

                override fun onNext(t: ShowProfileResponse?) {
                    view.setProfile(t?.name)
                }

                override fun onError(e: Throwable?) {
                    view.onFail("拿取個人資訊失敗")
                }

                override fun onComplete() {
                    view.onComplete("拿取個人資訊完成")
                }
            })
    }
}