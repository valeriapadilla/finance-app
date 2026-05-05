package com.upb.finanzasapp.model

//listas personalizadas
enum class TipoMovimiento{
    INGRESO,
    GASTO
}

enum class Categoria{
    CREDITO,
    GASTOS_FIJOS,
    MERCADO,
    ROPA,
    SALARIO,
    OTROS
}


//tipo de dato personalizado
data class Movimiento(
    val id: String,
    val descripcion: String,
    val monto: Double,
    val tipo: TipoMovimiento,
    val fecha: String,
    val categoria: Categoria
)
