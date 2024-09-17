package com.example.springclient.data

import com.example.springclient.network.StudentApiService
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val studentRepository: StudentRepository
}

class DefaultAppContainer : AppContainer {

    private val baseUrl = "http://192.168.0.101:8080/api"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: StudentApiService by lazy {
        retrofit.create(StudentApiService::class.java)
    }

    override val studentRepository : StudentRepository by lazy {
        NetworkStudentsRepository(retrofitService)
    }
}