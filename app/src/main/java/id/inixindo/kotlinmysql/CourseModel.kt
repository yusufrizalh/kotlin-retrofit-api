package id.inixindo.kotlinmysql

import java.io.Serializable

data class CourseModel(val courses: List<Data>) {
    data class Data(
        val id: String?,
        val name: String?,
        val duration: String?,
        val price: String?,
        val description: String?,
        val created_at: String?,
    ) : Serializable
}
