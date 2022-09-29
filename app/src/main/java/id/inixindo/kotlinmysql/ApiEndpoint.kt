package id.inixindo.kotlinmysql

import retrofit2.Call
import retrofit2.http.GET

interface ApiEndpoint {
    @GET("getAllCourses.php")   // mendapatkan data courses
    fun courses(): Call<CourseModel>
}