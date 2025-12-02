package com.example.ejemplogestures.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.ejemplogestures.utilidades.generaListaAleatoria

import androidx.compose.runtime.State

class MiViewModel: ViewModel() {

        private val _numeros = mutableStateOf(generaListaAleatoria())
        public val numeros: State<List<Int>> = _numeros
    // Cambiar un valor específico
    fun actualizarNumero(pos: Int, valor: Int) {

        _numeros.value[pos] = valor
    }
        // Opcional: función para reiniciar la lista
        fun reiniciar() {
            _numeros.value = generaListaAleatoria()
        }

}