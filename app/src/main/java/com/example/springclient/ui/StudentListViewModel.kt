package com.example.springclient.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.springclient.SpringClientApplication
import com.example.springclient.data.StudentRepository
import com.example.springclient.network.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface StudentsUiState {
    data class Success(val students: List<Student>) : StudentsUiState
    data object Error : StudentsUiState
    data object Loading : StudentsUiState
}

class ListViewModel(private val repo: StudentRepository) : ViewModel() {

    private val _studentsUiState = MutableStateFlow<StudentsUiState>(StudentsUiState.Loading)

    var studentsUiState = _studentsUiState.asLiveData()
        private set

    init {
        getStudents()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SpringClientApplication)
                val studentRepository = application.container.studentRepository
                ListViewModel(studentRepository)
            }
        }
    }

    fun getStudents() {
        viewModelScope.launch {
            _studentsUiState.value = StudentsUiState.Loading
            try {
                _studentsUiState.value = StudentsUiState.Success(repo.getStudents())
            } catch (e: IOException) {
                _studentsUiState.value = StudentsUiState.Error
            }
        }
    }

    fun deleteStudentIds(ids: List<Int>) {
        viewModelScope.launch {
            repo.deleteStudents(ids)
        }
    }

}
