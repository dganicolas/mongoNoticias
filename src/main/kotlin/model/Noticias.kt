package org.example.model

import java.text.SimpleDateFormat
import java.util.*

data class Noticias(
    val titulo: String,
    val cuerpo: String,
    val fechaPublicacion: Date,
    var autor: String,
    val tags: List<String>,
) {
    override fun toString(): String {
        return "Título: $titulo\n" +
                "Cuerpo: $cuerpo\n" +
                "Fecha de Publicación: ${SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(fechaPublicacion)}\n" +
                "Autor: $autor\n" +
                "Tags: ${tags.joinToString(", ")}"
    }
}