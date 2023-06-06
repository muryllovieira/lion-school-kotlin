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
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.model.Student
import br.senai.sp.jandira.lionschool.model.Students
import br.senai.sp.jandira.lionschool.model.StudentsList
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerformanceStudent : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                val matriculaAluno = intent.getStringExtra("matricula")
                Student(matriculaAluno.toString())
            }
        }
    }
}

@Composable
fun Performance(matricula: String) {
    val context = LocalContext.current

    var student by remember {
        mutableStateOf(Student("", "", "", emptyList()))
    }

    val call = RetrofitFactory().getStudentsService().getAlunosByMatricula(matricula)

    call.enqueue(object : Callback<Student> {
        override fun onResponse(call: Call<Student>, response: Response<Student>) {
            if (response.isSuccessful) {
                val studentResponse = response.body()
                if (studentResponse != null) {
                    student = studentResponse
                }
            } else {
                Log.e("teste", "Erro na resposta da API: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<Student>, t: Throwable) {
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
    ){
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
                        var openCourse = Intent(context, br.senai.sp.jandira.lionschool.model.Courses::class.java)

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
                    text = "Performance",
                    fontSize = 40.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
                Spacer(modifier = Modifier.size(40.dp))

                LazyColumn(){
                    items(student.curso){
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp)
                                .padding(20.dp),
                            backgroundColor = Color(51, 71, 176, 255),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(15.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.img),
                                    contentDescription = "",
                                    modifier = Modifier.size(130.dp)
                                )

                                Text(
                                    text = student.nome,
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 32.sp,
                                    textAlign = TextAlign.Center
                                )
                            }

                        }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp)
                                .padding(20.dp),
                            backgroundColor = Color(141, 155, 232, 255),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(text = "Grades", textAlign = TextAlign.Center)
                        }
                    }

                }

            }
    }
}
}




