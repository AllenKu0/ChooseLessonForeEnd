package com.example.rp0606.api

import com.example.rp0606.login.UserLoginData
import com.example.rp0606.chooseLesson.ChooseLessonRequest
import com.example.rp0606.chooseLesson.ChooseLessonResponse
import com.example.rp0606.showLesson.ShowLessonResponse
import com.example.rp0606.showOffice.ShowOfficeResponse
import com.example.rp0606.showTeacher.ShowTeacherResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ApiService {

    //登入
    @POST("/api/users/login")
    fun login(@Body userLogin: UserLoginData) : Completable

    //註冊
    @POST("/api/users/register")
    fun register(@Body userLogin: UserLoginData) : Completable

    //取得已選課
    @GET("/api/course/getByUser")
    fun getAllMyLesson(@Query("account") account:String) : Observable<List<ShowLessonResponse>>

    //取得所有課程
    @GET("/api/lesson/getNotSelect")
    fun getNotSelectLesson(@Query("account") account: String):Observable<List<ChooseLessonResponse>>

    @GET("/api/lesson/getAll")
    fun getAllLesson():Observable<List<ChooseLessonResponse>>

    //選課
    @POST("/api/course/select")
    fun chooseLesson(@Body chooseLessonRequest: ChooseLessonRequest):Completable

    //取得授課老師
    @GET("/api/teach/getTeacherByLesson")
    fun getTeacherByLesson(@Query("lessonId") lessonId:Long) : Observable<List<ShowTeacherResponse>>

    //取得老師辦公室資訊
    @GET("/api/belong/get")
    fun getOfficeByTeacher(@Query("teacherName") teacherName:String) :Observable<ShowOfficeResponse>


}