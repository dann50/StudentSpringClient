package com.example.springclient.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.springclient.databinding.StudentCardBinding
import com.example.springclient.network.Student

class StudentListRowHolder(
    private val binding: StudentCardBinding,
    private val onStudentClick: (Student) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(student: Student) {
        binding.name.text = student.name
        binding.dobValue.text = student.dob
        binding.deptMajorValue.text = "${student.dept} / ${student.major}"
        binding.root.setOnClickListener { onStudentClick(student) }
    }
}