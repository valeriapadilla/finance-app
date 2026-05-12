package com.upb.finanzasapp.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upb.finanzasapp.view.PantallaAgregar
import com.upb.finanzasapp.view.home.PantallaPrincipal
import com.upb.finanzasapp.viewModel.MovimientoViewModel

@Composable
fun AppNavigation(
    viewModel: MovimientoViewModel
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {

        composable("inicio") {

            PantallaPrincipal(
                puenteDatos = viewModel,

                irAgregar = {
                    navController.navigate("agregar")
                }
            )
        }

        composable("agregar") {

            PantallaAgregar(
                puenteDatos = viewModel,

                volver = {
                    navController.popBackStack()
                }
            )
        }
    }
}