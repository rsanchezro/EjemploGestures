package com.example.ejemplogestures

import android.R.attr.padding
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputEventHandler
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.rotary.onPreRotaryScrollEvent
import androidx.compose.ui.keepScreenOn
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ejemplogestures.ui.theme.EjemploGesturesTheme
import com.example.ejemplogestures.viewmodel.MiViewModel
import kotlin.math.abs

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            EjemploGesturesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(

                        Modifier.padding(innerPadding), MiViewModel()
                    )
                }
            }
        }
    }
}



@Composable
fun Greeting(modificador: Modifier= Modifier,miview: MiViewModel)
{
    var direccion by remember { mutableStateOf("") }
    var desplazamiento by remember { mutableStateOf(Offset(0f,0f)) }
    //Instancio el objeto viewModel que solo se va a instanciar 1 vez
    val miviewModel= MiViewModel()
    //declaro la variable numeros como un State
    val numeros by miview.numeros


    Box(
        modifier = modificador
            .fillMaxSize()
            ,
        contentAlignment = Alignment.Center
    ) {
        Column( horizontalAlignment = Alignment.CenterHorizontally ){

        Box(
            modifier = Modifier
                .background(Color.LightGray)
                /* Forma real de como esta definido la función pointerInput
                .pointerInput(Unit,object : PointerInputEventHandler {
                    override suspend fun PointerInputScope.invoke() {
                        Es una suspend function para poder invocar a funciones suspend

                    }

                })
                */

                //Controlamos la entrada de un puntero,
                //pero no lo vinculamos con nada(Unit),
                //lo podemos vincular con una variable que al cambiar
                //se ejecute el pointerInput, en este caso no queremos
                //preparamos el componente para recibir gestos
                .pointerInput(Unit){

                    //Gestionamos el gesto de arrastre
                    detectDragGestures (
                        //Cuando se inicia el gesto inicializamos el desplazamiento a 0
                        onDragStart = { desplazamiento = Offset.Zero },
                        //Cuando finaliza el gesto de arrastrar (levantamos el dedo del movil)
                        //calculamos el desplazamiento
                        onDragEnd = {
                            // Calculamos dirección final según desplazamiento
                            val (dx, dy) = desplazamiento
                            direccion = when {
                                //Al ser dy positivo estamos bajando
                                dy > 0 && abs(dy) > abs(dx) -> "Abajo"
                                dy < 0 && abs(dy) > abs(dx) -> "Arriba"
                                dx > 0 && abs(dx) > abs(dy) -> "Derecha"
                                dx < 0 && abs(dx) > abs(dy) -> "Izquierda"
                                else -> "Desconocido"
                            }
                        },
                        //Cuando arrastramos actualizamos el desplazamiento según el movimiento
                        //del cursor dragAmount
                        onDrag = { change, dragAmount ->
                            desplazamiento += dragAmount   // sumamos el movimiento
                            change.consume()
                        })

                }
                // Fondo del Box principal
                .padding(16.dp)
        ) {

            Column(//Espaciamos las filas en 8dp
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                //chunked genera una lista de una lista de 4 elementos cada sublista
                //con foreach recorremos cada sublista
                numeros.chunked(4).forEach { rowItems ->

                    Row(//Espaciamos las columnas en 8dp
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { number ->
                            Card(
                                modifier = Modifier.size(80.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.DarkGray
                                ),


                                ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                    // Color distinto al principal
                                    ,
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (number != -1) number.toString() else "",
                                        color = Color.White,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
                }
            }
            Text(modifier = Modifier.padding(top=64.dp), text="DIRECCION:$direccion", fontSize = 24.sp)
        }
    }
}

@Composable
fun Greeting1(name: String, modifier: Modifier = Modifier) {
    var posicion by remember { mutableStateOf( Offset(0f,0f)) }
    var density= LocalDensity.current
    var desplazamiento by remember { mutableStateOf(Offset(0f,0f)) }
    Text(
        text = "Posicionx:${posicion.x} Posicion_y:${posicion.y}; Desplazamiento:${desplazamiento.toString()} ",
        modifier = modifier.offset(with(density){posicion.x.toDp()},with(density){posicion.y.toDp()})
            .pointerInput(Unit){
                    detectDragGestures {change,offset->
                        change.consume()
                        desplazamiento=offset
                        posicion+=offset
                    }
            })

}


@Composable
fun GreetingPreview() {
    EjemploGesturesTheme {
       // Greeting()
    }
}