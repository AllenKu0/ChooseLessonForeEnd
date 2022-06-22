package com.example.rp0606.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

open class ApiBuilder {
    private var myAPIService: ApiService? = null

    //192.168.0.103
    //192.168.1.74
    constructor() {
        val retrofit :Retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.74:8090")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        myAPIService = retrofit.create(ApiService::class.java)
    }
    companion object{
        private val mInstance: ApiBuilder = ApiBuilder()
        open fun getInstance(): ApiBuilder {
            return mInstance
        }
    }

    open fun getAPI(): ApiService? {
        return myAPIService
    }
}