package org.example.model

data class Usuarios(
    val email: String,
    val nombre : String,
    val estado : Boolean,
    val direccionPostal:Direccion,
    val telefonos:MutableList<String>
)