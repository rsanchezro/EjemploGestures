package com.example.ejemplogestures.utilidades

/*
Funcion que devuelve una lista de 16 valores enteros, con dos elementos con
valores 2 o 4 y el resto -1
 */
fun generaListaAleatoria(): List<Int> {
    //Genero 16 elementos con valor -1
    val resultado = MutableList(16) { -1 }   // 14 valores -1 y luego reemplazamos 2

    // Elegimos 2 posiciones aleatorias distintas
    //shufled devuelve un List de enteros con los valores indicados ahí desordenados
    //aleatoriamente, y take toma los x primeros elementos de la lista y devuelve
    //otra lista
    val posiciones_aleatorias = (0 until 16).shuffled().take(2)

    // Asignamos a esas posiciones un 2 o un 4 aleatorio
    posiciones_aleatorias.forEach { index ->
        resultado[index] = listOf(2, 4).random()
    }

    return resultado
}