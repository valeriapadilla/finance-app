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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
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
    irAgregar: () -> Unit
) {
    //variables
    val movimientos by puenteDatos.movimientos.collectAsStateWithLifecycle()
    val cuenta by puenteDatos.cuenta.collectAsStateWithLifecycle()

    //TODO: filtrar por categoria
    var categoriaFiltro by remember { mutableStateOf<Categoria?>(null) }
    var expandedFiltro by remember { mutableStateOf(false) }

    val textoFiltro = categoriaFiltro?.name ?: "Todas"

    val movimientosFiltrados = if (categoriaFiltro != null) {
        puenteDatos.filtrarPorCateria(categoriaFiltro!!)
    } else {
        movimientos
    }


    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text("💰 Control de gastos")
                }
            )
        },

        floatingActionButton = {

            FloatingActionButton(
                onClick = irAgregar,
                containerColor = Color(0xFF7EBDA9),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar"
                )
            }
        }

    ) { padding ->


        //vista
        Column(
            modifier = modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Cuenta: ${cuenta.nombre}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            Text(
                text = "Saldo: ${cuenta.saldo}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                ExposedDropdownMenuBox(
                    expanded = expandedFiltro,
                    onExpandedChange = { expandedFiltro = !expandedFiltro },
                    modifier = Modifier.width(200.dp)
                ) {
                    OutlinedTextField(
                        value = textoFiltro,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Filtrar por categoría") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expandedFiltro) },
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

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Movimientos",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(movimientosFiltrados.size) { index ->

                    val mov = movimientosFiltrados[index]

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF7EBDA9) // color del fondo del card
                        )
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Column {

                                Text(mov.descripcion, color = Color.White)

                                Text(
                                    mov.categoria.name,
                                    color = Color.White
                                )
                            }

                            Text("$${mov.monto}", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}