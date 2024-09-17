package com.example.springclient.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.springclient.SpringClientApplication
import com.example.springclient.data.StudentRepository
import com.example.springclient.network.Student
import kotlinx.coroutines.launch

class StudentDisplayViewModel(private val repo: StudentRepository) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SpringClientApplication)
                val studentRepository = application.container.studentRepository
                StudentDisplayViewModel(studentRepository)
            }
        }
    }

    fun save(student: Student) {
        viewModelScope.launch {
            repo.saveStudent(student)
            repo.getStudents()
        }
    }

    fun update(student: Student) {
        viewModelScope.launch {
            repo.updateStudent(student)
        }
    }

    fun loadStudent(id: Int) : Student {
        return repo.loadStudentFromList(id)
    }

}