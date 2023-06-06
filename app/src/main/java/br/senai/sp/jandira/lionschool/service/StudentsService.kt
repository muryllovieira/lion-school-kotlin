package br.senai.sp.jandira.lionschool.service

import br.senai.sp.jandira.lionschool.model.Student
import br.senai.sp.jandira.lionschool.model.StudentsList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StudentsService {

    //url padrao
    //https://lion-school-2023.cyclic.app/v1/lion-school/alunos?curso=ds

    @GET("alunos")
    fun getCourseStudent(@Query("curso") siglaCourse: String): Call<StudentsList>

    @GET("alunos/{matricula}")
    fun getAlunosByMatricula(@Path("matricula") matricula: String): Call<Student>
}