package com.upb.finanzasapp.Data
import com.upb.finanzasapp.model.Movimiento
import com.upb.finanzasapp.model.TipoMovimiento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MovimientoFuenteDatos {

    //lista de movimientos  tiene que ser mutable y tener constante escucha por
    //que cambia en tiempo
    private val _movimientos = MutableStateFlow<List<Movimiento>>(emptyList())

    val movimientos: StateFlow<List<Movimiento>> = _movimientos.asStateFlow()
    //val nombreVariable: tipoDato = valor

    private val _saldo = MutableStateFlow(0.0)

    val saldo: StateFlow<Double> = _saldo.asStateFlow()

    //CRUD

    //C - CREAR
    fun agregarMovimiento(mov: Movimiento){
        val nuevoSaldo =
            if (mov.tipo == TipoMovimiento.INGRESO){
                _saldo.value = _saldo.value + mov.monto
            } else {
                _saldo.value = _saldo.value - mov.monto

            }

        _movimientos.value = _movimientos.value + mov
    }

    //R-Leer
    fun obtenerMovimientos(): List<Movimiento>{
        return movimientos.value
    }

    fun obtenerSaldo(): Double {
        return saldo.value
    }

    //U-Actualizar


    //actualizar...
}