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

class CreateActivity : AppCompatActivity() {
    private lateinit var editCourseName: EditText
    private lateinit var editCourseDuration: EditText
    private lateinit var editCoursePrice: EditText
    private lateinit var editCourseDescription: EditText
    private lateinit var buttonCreate: Button

    private val api by lazy { ApiRetrofit().endpoint }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        supportActionBar!!.title = "Create New Course"
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        editCourseName = findViewById(R.id.editCourseName)
        editCourseDuration = findViewById(R.id.editCourseDuration)
        editCoursePrice = findViewById(R.id.editCoursePrice)
        editCourseDescription = findViewById(R.id.editCourseDescription)
    }

    private fun setupListeners() {
        buttonCreate = findViewById(R.id.button_create)
        buttonCreate.setOnClickListener {
            if (editCourseName.text.toString().isNotEmpty() &&
                editCourseDuration.text.toString().isNotEmpty() &&
                editCoursePrice.text.toString().isNotEmpty() &&
                editCourseDescription.text.toString().isNotEmpty()
            ) {
                api.create(
                    editCourseName.text.toString(),
                    editCourseDuration.text.toString(),
                    editCoursePrice.text.toString(),
                    editCourseDescription.text.toString()
                ).enqueue(object : Callback<MessageModel> {
                    override fun onResponse(
                        call: Call<MessageModel>,
                        response: Response<MessageModel>
                    ) {
                        if (response.isSuccessful) {
                            val create = response.body()
                            Toast.makeText(applicationContext, create!!.message, Toast.LENGTH_LONG)
                                .show()
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<MessageModel>, t: Throwable) {
                        Log.e("CreateActivity", t.toString())
                    }

                })
            } else {
                Toast.makeText(applicationContext, "Fields can not be empty", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}