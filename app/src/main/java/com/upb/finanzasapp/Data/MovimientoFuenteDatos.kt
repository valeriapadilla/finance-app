package com.upb.finanzasapp.Data
import com.upb.finanzasapp.model.Cuenta
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

    //variables para cuenta
    private val _cuenta = MutableStateFlow(
        Cuenta("1","Bancolombia",0.0)
    )
    val cuenta: StateFlow<Cuenta> = _cuenta.asStateFlow()

    //CRUD

    //C - CREAR
    fun agregarMovimiento(mov: Movimiento){
        val cuentaActual = _cuenta.value

        val nuevoSaldo =
            if (mov.tipo == TipoMovimiento.INGRESO){
                cuentaActual.saldo + mov.monto
//                _saldo.value = _saldo.value + mov.monto
            } else {
                cuentaActual.saldo - mov.monto
//                _saldo.value = _saldo.value - mov.monto
            }

        //actualizacion del saldo a la variable general _cuenta
        _cuenta.value = cuentaActual.copy(saldo = nuevoSaldo)

        _movimientos.value = _movimientos.value + mov
    }

    //R-Leer
    fun obtenerMovimientos(): List<Movimiento>{
        return movimientos.value
    }

    fun obtenerSaldo(): Double {
        return cuenta.value.saldo
    }

    //U-Actualizar


    //actualizar...
}