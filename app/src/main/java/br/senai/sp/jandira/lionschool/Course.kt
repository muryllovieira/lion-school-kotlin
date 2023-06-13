package br.senai.sp.jandira.lionschool

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.model.Courses
import br.senai.sp.jandira.lionschool.model.CoursesList
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Course : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                Courses()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Courses() {
    val context = LocalContext.current

    var nameCourseState by remember {
        mutableStateOf("")
    }

    var listCourses by remember {
        mutableStateOf(listOf<Courses>())
    }

    val call = RetrofitFactory().getCourseService().getCourse()

    call.enqueue(object : Callback<CoursesList> {
        override fun onResponse(call: Call<CoursesList>, response: Response<CoursesList>) {
            listCourses = response.body()!!.cursos
        }

        override fun onFailure(call: Call<CoursesList>, t: Throwable) {
            Log.i("teste", "onFailure: ${t.message}")
        }
    })

    var listaCards = listCourses

    fun filterByName (name: String) {

        var listaNova = listCourses.filter {
            val regex = Regex(name, RegexOption.IGNORE_CASE)
            it.sigla.contains(regex)
        }
        if(!listaNova.isEmpty()){
            listaCards = listaNova
        }else if(name == ""){
            listaCards = listCourses
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(54, 60, 207), Color(255, 255, 255)
                    )
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {}
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        var openCourse = Intent(context, MainActivity::class.java)

                        context.startActivity(openCourse)
                    }
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Choose the course you want to access",
                    fontSize = 32.sp,
                    color = Color.White,
                    modifier = Modifier.width(290.dp),
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
                Spacer(modifier = Modifier.size(25.dp))
                OutlinedTextField(
                    value = nameCourseState,
                    onValueChange = {
                        nameCourseState = it

                        filterByName(it)
                    },
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Green,
                        unfocusedBorderColor = Color.White
                    ),
                    label = {
                        Text(text = "Search for a course", color = Color.White)
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_search_24),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                )
                Spacer(modifier = Modifier.size(20.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                ) {
                    items(listaCards) {
                        Spacer(modifier = Modifier.size(20.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .clickable {
                                    var openStudents = Intent(context, Students::class.java)
                                    openStudents.putExtra("sigla", it.sigla)

                                    context.startActivity(openStudents)
                                },
                            backgroundColor = Color(208, 220, 238),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row(modifier = Modifier.padding(10.dp)) {
                                AsyncImage(
                                    model = it.icone,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(75.dp)
                                )
                                Spacer(modifier = Modifier.size(10.dp))
                                Column() {
                                    Text(
                                        text = it.sigla,
                                        fontSize = 35.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    Text(text = it.nome, fontSize = 15.sp)
                                }
                            }

                        }
                    }
                }

            }

        }
    }
}