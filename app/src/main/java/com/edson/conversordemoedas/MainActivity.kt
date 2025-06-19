package com.edson.conversordemoedas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.edson.conversordemoedas.ui.theme.ConversorDeMoedasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConversorDeMoedasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ConversorScreen()
                }
            }
        }
    }
}

@Composable
fun ConversorScreen() {
    var valorInput by remember { mutableStateOf("") }
    var moedaOrigem by remember { mutableStateOf("BRL") }
    var moedaDestino by remember { mutableStateOf("USD") }
    var resultado by remember { mutableStateOf("") }

    val moedas = listOf("BRL", "USD", "EUR")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Conversor de Moedas", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = valorInput,
            onValueChange = { valorInput = it },
            label = { Text("Valor a converter") },
            singleLine = true
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DropdownMenuMoeda(
                selected = moedaOrigem,
                onSelect = { moedaOrigem = it },
                moedas = moedas,
                label = "De"
            )
            DropdownMenuMoeda(
                selected = moedaDestino,
                onSelect = { moedaDestino = it },
                moedas = moedas,
                label = "Para"
            )
        }

        Button(onClick = {
            resultado = converterMoeda(valorInput, moedaOrigem, moedaDestino)
        }) {
            Text("Converter")
        }

        if (resultado.isNotEmpty()) {
            Text("Resultado: $resultado", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
fun converterMoeda(valorStr: String, origem: String, destino: String): String {
    val taxas = mapOf(
        "BRL" to 1.0,
        "USD" to 0.19,
        "EUR" to 0.18
    )

    val valor = valorStr.toDoubleOrNull()
    if (valor == null || origem !in taxas || destino !in taxas) {
        return "Erro na conversão"
    }

    val emReal = valor / taxas[origem]!!
    val convertido = emReal * taxas[destino]!!

    return "%.2f $destino".format(convertido)
}


@Composable
fun DropdownMenuMoeda(
    selected: String,
    onSelect: (String) -> Unit,
    moedas: List<String>,
    label: String
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(label)
        OutlinedButton(onClick = { expanded = true }) {
            Text(selected)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            moedas.forEach { moeda ->
                DropdownMenuItem(
                    text = { Text(moeda) },
                    onClick = {
                        onSelect(moeda)
                        expanded = false
                    }
                )
            }
        }
    }
}

