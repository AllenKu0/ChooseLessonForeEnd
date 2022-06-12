package com.example.rp0606.login

import android.util.Log
import com.example.rp0606.api.ApiBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginPresenter(var loginView: LoginContract.View):
    LoginContract.Presenter {

    override fun loginApi(userLogin: UserLoginData) {
        ApiBuilder.getInstance().getAPI()?.login(userLogin)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable?) {
                    loginView.loginProcess()
                }

                override fun onComplete() {
                    loginView.loginSuccess()
                }

                override fun onError(e: Throwable?) {
                    Log.e("TAG", "onError: $e")
                    loginView.loginError()
                }
            })
    }
}