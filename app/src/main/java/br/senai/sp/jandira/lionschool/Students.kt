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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.model.Courses
import br.senai.sp.jandira.lionschool.model.CoursesList
import br.senai.sp.jandira.lionschool.model.StudentsList
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Students : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                val siglaCurso = intent.getStringExtra("sigla")
                Student(siglaCurso.toString())
            }
        }
    }
}

@Composable
fun Student(curso: String) {
    val context = LocalContext.current

    var listStudents by remember {
        mutableStateOf(listOf<br.senai.sp.jandira.lionschool.model.Students>())
    }

    var nameCourse by remember {
        mutableStateOf("")
    }

    val call = RetrofitFactory().getStudentsService().getCourseStudent(curso)

    call.enqueue(object : Callback<StudentsList> {
        override fun onResponse(call: Call<StudentsList>, response: Response<StudentsList>) {
            listStudents = response.body()!!.alunos
            nameCourse = response.body()!!.nomeCurso
        }

        override fun onFailure(call: Call<StudentsList>, t: Throwable) {
            Log.i("teste", "onFailure: ${t.message} ")
        }
    })

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
                    text = "Course Students",
                    fontSize = 32.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
                Spacer(modifier = Modifier.size(15.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Green,
                        unfocusedBorderColor = Color.White
                    ),
                    label = {
                        Text(text = "Search for a student", color = Color.White)
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
                Text(
                    text = nameCourse, fontSize = 25.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
                Row() {
                    Switch(checked = false, onCheckedChange = {})
                    Switch(checked = true, onCheckedChange = {})
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp)
                ) {
                    items(listStudents) {
                        Spacer(modifier = Modifier.size(20.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp),
                            backgroundColor = Color(255, 194, 62),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(15.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    model = it.foto,
                                    contentDescription = "",
                                    modifier = Modifier.size(130.dp)
                                )

                                Text(
                                    text = it.nome,
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 32.sp,
                                    textAlign = TextAlign.Center
                                )
                            }

                        }
                    }
                }

            }

        }
    }
}

