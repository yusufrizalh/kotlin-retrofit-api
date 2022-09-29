package id.inixindo.kotlinmysql

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var editCourseName: EditText
    private lateinit var editCourseDuration: EditText
    private lateinit var editCoursePrice: EditText
    private lateinit var editCourseDescription: EditText
    private lateinit var buttonUpdate: Button

    private val api by lazy { ApiRetrofit().endpoint }
    private val course by lazy { intent.getSerializableExtra("course") as CourseModel.Data }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar!!.title = "Detail Course"
        setupViews()
        setupListeners()
    }

    private fun setupListeners() {
        buttonUpdate.setOnClickListener {
            api.update(
                course.id!!,
                editCourseName.text.toString(),
                editCourseDuration.text.toString(),
                editCoursePrice.text.toString(),
                editCourseDescription.text.toString(),
            ).enqueue(object : Callback<MessageModel> {
                override fun onResponse(
                    call: Call<MessageModel>,
                    response: Response<MessageModel>
                ) {
                    if (response.isSuccessful) {
                        val update = response.body()
                        Toast.makeText(applicationContext, update!!.message, Toast.LENGTH_LONG)
                            .show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<MessageModel>, t: Throwable) {
                    Log.e("DetailActivity", t.toString())
                }

            })
        }
    }

    private fun setupViews() {
        // mengenali komponen dalam layout
        editCourseName = findViewById(R.id.edit_course_name)
        editCourseDuration = findViewById(R.id.edit_course_duration)
        editCoursePrice = findViewById(R.id.edit_course_price)
        editCourseDescription = findViewById(R.id.edit_course_description)
        buttonUpdate = findViewById(R.id.button_update)

        // menulis isi nilai dari setiap komponen
        editCourseName.setText(course.name)
        editCourseDuration.setText(course.duration)
        editCoursePrice.setText(course.price)
        editCourseDescription.setText(course.description)
    }
}