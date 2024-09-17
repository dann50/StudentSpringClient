package com.example.springclient.data

import com.example.springclient.network.Student
import com.example.springclient.network.StudentApiService

interface StudentRepository {
    suspend fun getStudents() : List<Student>
    suspend fun saveStudent(student: Student) : Student
    suspend fun updateStudent(student: Student) : Student
    suspend fun deleteStudents(studentIds: List<Int>)
    fun loadStudentFromList(id: Int) : Student
}

class NetworkStudentsRepository(private val studentApiService: StudentApiService) : StudentRepository {

    private lateinit var students: MutableList<Student>

    override suspend fun getStudents(): List<Student> {
        val list = studentApiService.getStudents()
        students = list.toMutableList()
        return students
    }

    override fun loadStudentFromList(id: Int) : Student {
        return students.first { it.id == id }
    }

    override suspend fun saveStudent(student: Student): Student {
        students.add(student)
        return studentApiService.saveStudent(student)
    }

    override suspend fun updateStudent(student: Student): Student {
        val c = students.indexOf(students.find { it.id == student.id })

        students[c].apply {
            name = student.name
            dob = student.dob
            dept = student.dept
            major = student.major
        }

        return studentApiService.updateStudent(student)
    }


    override suspend fun deleteStudents(studentIds: List<Int>) {
        return studentApiService.deleteStudent(studentIds)
    }

}