package com.example.springclient.network

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface StudentApiService {
    @GET("students/")
    suspend fun getStudents(): List<Student>

    @POST("students/")
    suspend fun saveStudent(@Body student: Student) : Student
	
	@PUT("students/")
	suspend fun updateStudent(@Body student: Student) : Student

    @DELETE("students/")
    suspend fun deleteStudent(@Body studentIds: List<Int>)
}