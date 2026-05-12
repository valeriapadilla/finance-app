package com.upb.finanzasapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.upb.finanzasapp.model.Categoria
import com.upb.finanzasapp.model.TipoMovimiento
import com.upb.finanzasapp.viewModel.MovimientoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgregar(
    puenteDatos: MovimientoViewModel,
    volver: () -> Unit
) {

    var descripcion by remember {
        mutableStateOf("")
    }

    var monto by remember {
        mutableStateOf("")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    var categoriaSeleccionada by remember {
        mutableStateOf(Categoria.GASTOS_FIJOS)
    }


    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Agregar movimiento")
                },

                navigationIcon = {

                    IconButton(
                        onClick = volver
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }

    ) { padding ->


        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
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
                        volver()
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
                        volver()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray
                    )
                ) {
                    Text("Gasto")
                }
            }
        }
    }

}