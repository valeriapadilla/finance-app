package com.upb.finanzasapp.viewModel

import com.upb.finanzasapp.Data.MovimientoFuenteDatos
import com.upb.finanzasapp.model.Categoria
import com.upb.finanzasapp.model.Movimiento
import com.upb.finanzasapp.model.TipoMovimiento
import java.util.UUID

class MovimientoViewModel(private val datos: MovimientoFuenteDatos = MovimientoFuenteDatos()) {

    val movimientos = datos.movimientos
    val saldo = datos.saldo

    val cuenta = datos.cuenta

    //funciones necesarias
    fun agregarMovimiento(
        descripcion: String,
        monto: String,
        tipo: TipoMovimiento,
        categoria: Categoria
    ){
        val mov = Movimiento(
            id = UUID.randomUUID().toString(),
            descripcion = descripcion,
            monto = monto.toDoubleOrNull() ?: 0.0,
            tipo = tipo,
            categoria = categoria,
            fecha = System.currentTimeMillis().toString(),
            cuentaId = "1"
        )
        datos.agregarMovimiento(mov)
    }

    //lambdas
    fun filtrarPorCateria(cat: Categoria): List<Movimiento>{
        return movimientos.value.filter {  it.categoria == cat }
    }

}