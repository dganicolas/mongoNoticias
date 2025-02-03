package org.example.service

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import org.example.model.Comentarios
import org.example.utils.Logs
import org.example.utils.SesionIniciada

class ComentariosService(private val database: MongoDatabase) {

    fun escribirComentarios(comentarios: Comentarios): String {
        comentarios.autor = SesionIniciada.usuarioActivo
        val collection: MongoCollection<Comentarios> =
            database.getCollection("collComentarios", Comentarios::class.java)
        Logs.escribirLog("[Correcto] fun escribirComentarios " + collection.insertOne(comentarios).toString())
        return "la noticia \n $comentarios\nh a sido publicada"
    }

    fun listarComentarios(titulo: String):String {
        val collection: MongoCollection<Comentarios> = database.getCollection("collComentarios", Comentarios::class.java)
        var lista = ""
        collection.find(Filters.eq("noticia", titulo)).forEach {
            lista += "\n-----------------------------------COMENTARIOS DE $titulo--------------------------------------\n"
            lista += it.toString()
        }
        Logs.escribirLog("[correcto] fun listarComentarios $lista")
        return lista
    }
}