package com.upb.finanzasapp

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upb.finanzasapp.ui.theme.FinanzasAppTheme
import org.intellij.lang.annotations.JdkConstants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinanzasAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Saludo(
                        nombre="Android",
                        modificador = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Composable
fun GreetingPreview() {
    Greeting(name="Maria")
}

@Composable
@Preview(showBackground = true)
fun Saludo(
    nombre: String = "Android",
    modificador: Modifier = Modifier
){
    Column(modifier = modificador.size(100.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Text(
            text = "Hola $nombre",
            modifier = modificador
                .background(color = Color.Red)
            ,
            fontSize = 10.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Hola $nombre",
            modifier = modificador
                .background(color = Color.Blue)
            ,
            fontSize = 10.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
@Preview(showBackground = true)
fun SaludoFila(
    nombre: String = "Android",
    modificador: Modifier = Modifier
){
    Row(modifier = modificador.width(300.dp).height(50.dp),
        horizontalArrangement = Arrangement.spacedBy(100.dp),
//        verticalAlignment = Alignment.CenterVertically
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = "Hola $nombre",
            modifier = modificador
                .background(color = Color.Red)
            ,
            fontSize = 10.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Hola $nombre",
            modifier = modificador
                .background(color = Color.Blue)
            ,
            fontSize = 10.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SaludoBox(
    nombre: String = "Android",
    modificador: Modifier = Modifier
){
    Box(
    ) {
        Text(
            text = "Hola $nombre",
            modifier = modificador.size(100.dp)
                .background(color = Color.Red)
            ,
            fontSize = 10.sp,
            color = Color.White,
            textAlign = TextAlign.End
        )

        Text(
            text = "Hola $nombre",
            modifier = modificador
                .background(color = Color.Blue)
            ,
            fontSize = 10.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}



