package com.example.rp0606.showOffice

import android.util.Log
import com.example.rp0606.api.ApiBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ShowOfficePresenter(val view:ShowOfficeContract.View):ShowOfficeContract.Presenter {
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
                    view.setOfficeList(t)
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