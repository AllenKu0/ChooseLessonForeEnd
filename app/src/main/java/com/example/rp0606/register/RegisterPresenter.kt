package com.example.rp0606.register

import com.example.rp0606.api.ApiBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RegisterPresenter(val view: RegisterContract.View) : RegisterContract.Presenter {

    override fun register(registerRequest: RegisterRequest) {
        ApiBuilder.getInstance().getAPI()?.register(registerRequest)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable?) {
                    view.registerProcess()
                }

                override fun onComplete() {
                    view.registerComplete()
                }

                override fun onError(e: Throwable?) {
                    view.registerFail()
                }
            })

    }
}