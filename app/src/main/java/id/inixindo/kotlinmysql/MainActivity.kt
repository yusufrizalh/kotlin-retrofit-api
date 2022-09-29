package id.inixindo.kotlinmysql

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
                Toast.makeText(applicationContext, "$course", Toast.LENGTH_LONG).show()
            }

            override fun onDelete(course: CourseModel.Data) {
                Toast.makeText(applicationContext, "Delete Course", Toast.LENGTH_LONG).show()
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
            Toast.makeText(applicationContext, "Create New Course", Toast.LENGTH_LONG).show()
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