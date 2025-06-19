package com.edson.conversordemoedas.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ConversorViewModel : ViewModel() {

    private val taxas = mapOf(
        "BRL" to 1.0,
        "USD" to 0.19,
        "EUR" to 0.18
    )

    private val _valorConvertido = MutableStateFlow("")
    val valorConvertido: StateFlow<String> = _valorConvertido

    fun converter(valorStr: String, origem: String, destino: String) {
        val valor = valorStr.toDoubleOrNull()
        if (valor == null || origem !in taxas || destino !in taxas) {
            _valorConvertido.value = "Erro na conversão"
            return
        }

        val emReal = valor / taxas[origem]!!
        val convertido = emReal * taxas[destino]!!
        _valorConvertido.value = "%.2f $destino".format(convertido)
    }
}
