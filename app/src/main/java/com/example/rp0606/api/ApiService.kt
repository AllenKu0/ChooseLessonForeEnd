package com.example.rp0606.api

import com.example.rp0606.showProfile.ShowProfileRequest
import com.example.rp0606.login.LoginRequest
import com.example.rp0606.chooseLesson.ChooseLessonRequest
import com.example.rp0606.chooseLesson.ChooseLessonResponse
import com.example.rp0606.register.RegisterRequest
import com.example.rp0606.showClassRoom.ShowClassRoomResponse
import com.example.rp0606.showLesson.DropLessonRequest
import com.example.rp0606.showLesson.ShowLessonResponse
import com.example.rp0606.showOffice.ShowOfficeResponse
import com.example.rp0606.showProfile.ShowProfileResponse
import com.example.rp0606.showTeacher.ShowTeacherResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ApiService {

    //登入
    @POST("/api/users/login")
    fun login(@Body userLogin: LoginRequest) : Completable

    //註冊
    @POST("/api/users/register")
    fun register(@Body registerRequest: RegisterRequest) : Completable

    //取得已選課
    @GET("/api/course/getByUser")
    fun getAllMyLesson(@Query("account") account:String) : Observable<List<ShowLessonResponse>>

    //取得所有未選課程
    @GET("/api/lesson/getNotSelect")
    fun getNotSelectLesson(@Query("account") account: String):Observable<List<ChooseLessonResponse>>
    //取得所有課程
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

    //取得課程教室資訊
    @GET("/api/TeachOn/getClassRoomByLesson")
    fun getClassRoomByLesson(@Query("lessonName") lessonName:String) :Observable<ShowClassRoomResponse>

    //退選
    @POST("/api/course/delete")
    fun dropOutLesson(@Body dropLessonRequest: DropLessonRequest) : Completable

    //更新個人資料
    @PUT("api/users/updateProfile/{account}")
    fun updateProfile(@Path("account") account: String,@Body showProfileRequest: ShowProfileRequest): Completable

    //更新密碼
    @PUT("api/users/updatePassword/{account}")
    fun updatePassword(@Path("account") account: String,@Query("password")  newPassword:String): Completable

    //獲得學生個人資訊
    @GET("api/users/get")
    fun getStudentProfile(@Query("account") account:String):Observable<ShowProfileResponse>

}