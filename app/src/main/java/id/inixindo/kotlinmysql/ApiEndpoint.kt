package id.inixindo.kotlinmysql

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiEndpoint {
    @GET("getAllCourses.php")   // mendapatkan data courses
    fun courses(): Call<CourseModel>

    @FormUrlEncoded
    @POST("updateCourse.php")
    fun update(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("duration") duration: String,
        @Field("price") price: String,
        @Field("description") description: String,
    ): Call<MessageModel>

    @FormUrlEncoded
    @POST("deleteCourse.php")
    fun delete(
        @Field("id") id: String
    ): Call<MessageModel>

    @FormUrlEncoded
    @POST("createCourse.php")
    fun create(
        @Field("name") name: String,
        @Field("duration") duration: String,
        @Field("price") price: String,
        @Field("description") description: String,
    ): Call<MessageModel>
}