package org.example.model

import java.text.SimpleDateFormat
import java.util.*

data class Comentarios(
    var autor:String,
    val noticia:String,
    val comentario:String,
    val fechaHora: Date
) {
    override fun toString(): String {
        return "Autor: $autor\n" +
                "Noticia: $noticia\n" +
                "Comentario: $comentario\n" +
                "Fecha y Hora: ${SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(fechaHora)}"
    }
}