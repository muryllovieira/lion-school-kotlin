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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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
                Performance(matriculaAluno.toString())
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
                Spacer(modifier = Modifier.size(10.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(student.curso) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(290.dp)
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
                                AsyncImage(
                                    model = student.foto,
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
                                .height(390.dp)
                                .padding(20.dp),
                            shape = RoundedCornerShape(10.dp),
                            backgroundColor = colorResource(id = R.color.third_blue_gradient)
                        ) {
                            Spacer(modifier = Modifier.height(10.dp))
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                items(student.curso[0].disciplinas) {
                                    var barra = 2.4 * it.media.toDouble()
                                    var corBarra = colorResource(id = R.color.blue_default)

                                    if (it.media.toDouble() > 60) {
                                        corBarra = colorResource(id = R.color.blue_default)
                                    } else if (it.media.toDouble() < 60 && it.media.toDouble() > 50) {
                                        corBarra = colorResource(id = R.color.yellow_default)
                                    } else {
                                        corBarra = Color.Red
                                    }
                                    Spacer(modifier = Modifier.height(15.dp))
                                    Column(
                                        modifier = Modifier
                                            .width(240.dp)
                                            .height(40.dp)
                                    ) {
                                        Text(
                                            text = it.nome,
                                            fontWeight = FontWeight(700),
                                            fontSize = 12.sp,
                                            color = colorResource(id = R.color.black)
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Box(
                                            modifier = Modifier
                                                .height(17.5.dp)
                                                .width(240.dp)
                                                .clip(RoundedCornerShape(10.dp))
                                                .background(
                                                    colorResource(id = R.color.second_blue)
                                                )
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .background(
                                                        corBarra
                                                    )
                                                    .width(barra.dp)
                                                    .padding(0.dp, 0.dp, 5.dp, 0.dp),
                                                contentAlignment = Alignment.CenterEnd
                                            ) {
                                                Text(
                                                    text = it.media + "%",
                                                    fontWeight = FontWeight(700),
                                                    fontSize = 12.sp,
                                                    color = colorResource(id = R.color.white)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }
        }
    }
}





