package com.example.rp0606.alertPassword

import com.example.rp0606.api.ApiBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class AlertPasswordPresenter(val view: AlertPasswordContract.View) :
    AlertPasswordContract.Presenter {
    override fun alertPassword(account: String, newPassword: String) {
        ApiBuilder.getInstance().getAPI()?.updatePassword(account, newPassword)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable?) {
                    view.alertProcess("更新密碼中")
                }

                override fun onComplete() {
                    view.alertComplete()
                }

                override fun onError(e: Throwable?) {
                    view.alertFail("更新密碼失敗")
                }

            })
    }
}