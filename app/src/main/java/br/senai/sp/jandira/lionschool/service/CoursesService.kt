package br.senai.sp.jandira.lionschool.service

import br.senai.sp.jandira.lionschool.model.CoursesList
import retrofit2.Call
import retrofit2.http.GET

interface CoursesService {

    //https://rickandmortyapi.com/api/

    @GET("cursos")
    fun getCourse(): Call<CoursesList>

}