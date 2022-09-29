package id.inixindo.kotlinmysql

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val api by lazy { ApiRetrofit().endpoint }
    private lateinit var listCourses: RecyclerView
    private lateinit var courseAdapter: CourseAdapter
    private lateinit var fabCreate: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupCourseList()
        setupViews()
        setupListener()
    }

    private fun setupCourseList() {
        listCourses = findViewById(R.id.listCourses)
        courseAdapter = CourseAdapter(arrayListOf(), object : CourseAdapter.OnAdapterListener {
            override fun onClick(course: CourseModel.Data) {
                startActivity(
                    Intent(
                        applicationContext,
                        DetailActivity::class.java
                    ).putExtra("course", course)
                )
            }

            override fun onDelete(course: CourseModel.Data) {
                // sebelum menghapus buatkan sebuah konfirmasi dengan alert dialog

                api.delete(course.id!!).enqueue(object : Callback<MessageModel> {
                    override fun onResponse(
                        call: Call<MessageModel>,
                        response: Response<MessageModel>
                    ) {
                        if (response.isSuccessful) {
                            val delete = response.body()
                            Toast.makeText(applicationContext, delete!!.message, Toast.LENGTH_LONG)
                                .show()
                            getAllCourses()
                        }
                    }

                    override fun onFailure(call: Call<MessageModel>, t: Throwable) {
                        Log.e("MainActivity", t.toString())
                    }

                })
            }
        })
        listCourses.adapter = courseAdapter
    }

    private fun setupViews() {
        listCourses = findViewById(R.id.listCourses)
        fabCreate = findViewById(R.id.fabCreate)
    }

    private fun setupListener() {
        fabCreate.setOnClickListener {
            startActivity(Intent(applicationContext, CreateActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        getAllCourses()
    }

    private fun getAllCourses() {
        api.courses().enqueue(object : Callback<CourseModel> {
            override fun onResponse(call: Call<CourseModel>, response: Response<CourseModel>) {
                if (response.isSuccessful) {
                    val listCourses = response.body()!!.courses
                    listCourses.forEach {
                        Log.d("MainActivity", "Courses: ${it.name}")
                    }
                    courseAdapter.setData(listCourses)
                }
            }

            override fun onFailure(call: Call<CourseModel>, t: Throwable) {
                Log.e("MainActivity", t.toString())
            }
        })
    }
}