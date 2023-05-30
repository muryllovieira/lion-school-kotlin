package br.senai.sp.jandira.lionschool.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitFactory {

    private val BASE_URL = "https://lion-school-2023.cyclic.app/v1/lion-school/"

    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getCourseService(): CoursesService {
        return retrofitFactory.create(CoursesService::class.java)
    }

    fun getStudentsService(): StudentsService {
        return retrofitFactory.create(StudentsService::class.java)
    }
}