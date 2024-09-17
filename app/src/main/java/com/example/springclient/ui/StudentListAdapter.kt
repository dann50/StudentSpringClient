package com.example.springclient.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.springclient.databinding.StudentCardBinding
import com.example.springclient.network.Student

class StudentListAdapter(
    private val inflater: LayoutInflater,
    private val onRowClick: (Student) -> Unit
) : ListAdapter<Student, StudentListRowHolder>(StudentDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        StudentListRowHolder(StudentCardBinding.inflate(inflater, parent, false), onRowClick)

    override fun onBindViewHolder(holder: StudentListRowHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object StudentDiffCallback : DiffUtil.ItemCallback<Student>() {
    override fun areItemsTheSame(oldItem: Student, newItem: Student) = oldItem === newItem

    override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem.id == newItem.id &&
                oldItem.name == newItem.name &&
                oldItem.dob == newItem.dob &&
                oldItem.dept == newItem.dept &&
                oldItem.major == newItem.major
    }

}