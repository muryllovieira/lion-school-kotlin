package br.senai.sp.jandira.lionschool

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                Home()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Home() {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(245, 195, 17),
                        Color(94, 97, 192),
                        Color(94, 97, 192),
                        Color(0, 8, 195)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row() {
                    Image(
                        painter = painterResource(id = R.drawable.lion_school),
                        contentDescription = null,
                        modifier = Modifier
                            .width(82.dp)
                            .height(107.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Lion School",
                        fontSize = 32.sp,
                        color = Color.White,
                        modifier = Modifier.width(110.dp),
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = null,
                    modifier = Modifier
                        .width(250.dp)
                        .height(380.dp)
                )
                Button(
                    onClick = {
                        var openCourse = Intent(context, Course::class.java)

                        context.startActivity(openCourse)
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .width(300.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(Color(255, 194, 62))
                ) {
                    Text(
                        text = "GET STARTED",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_24),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }


        }
    }
}