package br.senai.sp.jandira.lionschool.model

data class Student(
    val foto: String,
    val nome: String,
    val matricula: String,
    val curso: List<Course>
)
