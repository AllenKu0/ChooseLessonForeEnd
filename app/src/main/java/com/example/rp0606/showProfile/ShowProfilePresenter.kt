package com.example.rp0606.showProfile

import com.example.rp0606.api.ApiBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ShowProfilePresenter(val view: ShowProfileContract.View) : ShowProfileContract.Presenter {
    override fun getProfile(account: String) {
        ApiBuilder.getInstance().getAPI()?.getStudentProfile(account)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<ShowProfileResponse> {
                override fun onSubscribe(d: Disposable?) {
                    view.onPrecessSolution("拿取個人資訊")
                }

                override fun onNext(t: ShowProfileResponse?) {
                    view.setProfile(t)
                }

                override fun onError(e: Throwable?) {
                    view.onErrorSolution("拿取個人資訊失敗")
                }

                override fun onComplete() {
                    view.onCompleteSolution()
                }
            })
    }

    override fun upDateProfile(account: String, showProfileRequest: ShowProfileRequest) {
        ApiBuilder.getInstance().getAPI()?.updateProfile(account,showProfileRequest)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : CompletableObserver{
                override fun onSubscribe(d: Disposable?) {
                    view.onPrecessSolution("更新個人資訊")
                }

                override fun onComplete() {
                    view.updateComplete()
                }

                override fun onError(e: Throwable?) {
                    view.onErrorSolution("更新個人資訊失敗")
                }
            })
    }
}