package id.inixindo.kotlinmysql

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CourseAdapter(val courses: ArrayList<CourseModel.Data>, val listener: OnAdapterListener) :
    RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtCourseName = view.findViewById<TextView>(R.id.txtCourseName)
        val imageDelete = view.findViewById<ImageView>(R.id.imageDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.activity_adapter_course, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = courses[position]
        holder.txtCourseName.text = data.name
        holder.itemView.setOnClickListener { listener.onClick(data) }
        holder.imageDelete.setOnClickListener { listener.onDelete(data) }
    }

    override fun getItemCount() = courses.size

    interface OnAdapterListener {
        fun onClick(course: CourseModel.Data)
        fun onDelete(course: CourseModel.Data)
    }

    fun setData(data: List<CourseModel.Data>) {
        courses.clear()
        courses.addAll(data)
        notifyDataSetChanged()
    }
}