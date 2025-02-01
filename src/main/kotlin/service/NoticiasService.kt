package org.example.service

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import org.example.model.Noticias

/*
Publicar noticias. *
• Listar las noticias publicadas por un usuario. *
• Buscar noticias por etiquetas.
• La fecha de publicación de una noticia debe ser única y no
editable.
* */
class NoticiasService(val database: MongoDatabase) {

    fun publicarNoticia(noticias: Noticias): String {
        val collection: MongoCollection<Noticias> = database.getCollection("collNoticias", Noticias::class.java)
        val filtroHora = Filters.eq("fechaPublicacion", noticias.fechaPublicacion)
        if(collection.find(filtroHora).first() == null){
            return collection.insertOne(noticias).toString()
        }else{
            return "no es posible publicar en este momento esta noticia intentalo mas tarde"
        }
    }

    fun listarNoticiasPorEtiqueta(etiquetas: List<String>): String {
        val collection: MongoCollection<Noticias> = database.getCollection("collNoticias", Noticias::class.java)
        //val filtroTags = Filters.regex("tags", etiquetas)
        if(collection.find(filtroTags).first() != null){
            var listaNoticias = ""
            collection.find(filtroTags).forEach {
                listaNoticias += "-----------------------------------NOTICIA DE $nombre--------------------------------------"
                listaNoticias += it.toString()
            }
            return listaNoticias
        }else{
            return "este usuario no ha publicado ninguna noticia"
        }
    }

    fun listarNoticiasPorUsuario(nombre: String): String {
        val collection: MongoCollection<Noticias> = database.getCollection("collNoticias", Noticias::class.java)
        val filtroUsuario = Filters.eq("autor", nombre)
        if(collection.find(filtroUsuario).first() != null){
            var listaNoticias = ""
            collection.find(filtroUsuario).forEach {
                listaNoticias += "-----------------------------------NOTICIA DE $nombre--------------------------------------"
                listaNoticias += it.toString()
            }
            return listaNoticias
        }else{
            return "este usuario no ha publicado ninguna noticia"
        }
    }
}