package com.upb.finanzasapp.view.home

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.upb.finanzasapp.model.Categoria
import com.upb.finanzasapp.model.TipoMovimiento
import com.upb.finanzasapp.viewModel.MovimientoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(
    puenteDatos: MovimientoViewModel,
    modifier: Modifier = Modifier,
) {
    //variables
    val movimientos by puenteDatos.movimientos.collectAsStateWithLifecycle()
    val saldo by puenteDatos.saldo.collectAsStateWithLifecycle()

    var descripcion by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }

    val cuenta by puenteDatos.cuenta.collectAsStateWithLifecycle()

    var expanded by remember { mutableStateOf(false) }
    var categoriaSeleccionada by remember { mutableStateOf(Categoria.OTROS) }

    //TODO: filtrar por categoria
    var categoriaFiltro by remember { mutableStateOf<Categoria?>(null) }
    var expandedFiltro by remember { mutableStateOf(false) }

    val textoFiltro = categoriaFiltro?.name ?: "Todas"

    val movimientosFiltrados = if (categoriaFiltro != null) {
        puenteDatos.filtrarPorCateria(categoriaFiltro!!)
    } else {
        movimientos
    }

    //vista
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "💰Control de gastos", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Cuenta: ${cuenta.nombre}",
            style = MaterialTheme.typography.bodyLarge, color = Color.Gray
        )
        Text(
            text = "Saldo: ${cuenta.saldo}",
            style = MaterialTheme.typography.bodyLarge, color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = categoriaSeleccionada.toString(),
                onValueChange = {},
                readOnly = true,
                label = { Text("Categoria") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                Categoria.entries.forEach { categoria ->
                    DropdownMenuItem(
                        text = {
                            Text(categoria.name)
                        },
                        onClick = {
                            categoriaSeleccionada = categoria
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripcion") },
            modifier = Modifier.fillMaxWidth()

        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = monto,
            onValueChange = { monto = it },
            label = { Text("Monto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(23.dp))

        //fila
        Row() {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    puenteDatos.agregarMovimiento(
                        descripcion,
                        monto,
                        TipoMovimiento.INGRESO,
                        categoriaSeleccionada
                    )
                    descripcion = ""
                    monto = ""
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray
                )

            ) {
                Text("Ingreso")
            }
            Spacer(modifier = Modifier.width(8.dp))

            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    puenteDatos.agregarMovimiento(
                        descripcion,
                        monto,
                        TipoMovimiento.GASTO,
                        categoriaSeleccionada
                    )
                    descripcion = ""
                    monto = ""
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray
                )
            ) {
                Text("Gasto")
            }
        }

        Spacer(modifier = Modifier.height(23.dp))

        Row(modifier= Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,) {
            ExposedDropdownMenuBox(
                expanded = expandedFiltro,
                onExpandedChange = { expandedFiltro = !expandedFiltro }
            ) {
                OutlinedTextField(
                    value = textoFiltro,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Filtrar por categoría") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedFiltro) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expandedFiltro,
                    onDismissRequest = { expandedFiltro = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text("Todas")
                        },
                        onClick = {
                            categoriaFiltro = null
                            expandedFiltro = false
                        }
                    )

                    Categoria.entries.forEach { categoria ->
                        DropdownMenuItem(
                            text = {
                                Text(categoria.name)
                            },
                            onClick = {
                                categoriaFiltro = categoria
                                expandedFiltro = false
                            }
                        )

                    }
                }

            }
        }

        Spacer(modifier = Modifier.height(23.dp))


        Text("Movimientos: ", style = MaterialTheme.typography.titleMedium)

        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            items(movimientos.size) {
                Text(
                    "${movimientos[it].descripcion} - ${movimientos[it].monto} - categoria: ${movimientos[it].categoria}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

    }
}
