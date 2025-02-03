package org.example.service

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import org.example.model.Noticias
import org.example.utils.Logs

/*
Publicar noticias. *
• Listar las noticias publicadas por un usuario. *
• Buscar noticias por etiquetas.
• La fecha de publicación de una noticia debe ser única y no
editable.
• Listar las 10 últimas noticias publicadas
* */
class NoticiasService(private val database: MongoDatabase) {

    fun publicarNoticia(noticias: Noticias): String {
        val collection: MongoCollection<Noticias> = database.getCollection("collNoticias", Noticias::class.java)
        val filtroHora = Filters.eq("fechaPublicacion", noticias.fechaPublicacion)
        if(collection.find(filtroHora).first() == null){
            Logs.escribirLog("[correcto] fun publicarNoticia ${collection.insertOne(noticias)}")
            return "la noticia \n $noticias\na sido publicada"
        }else{
            Logs.escribirLog("[incorrecto] fun publicarNoticia la noticia no puede tener el mismo id")
            return "no es posible publicar en este momento esta noticia intentalo mas tarde"
        }
    }

    fun listarLas10UltimasNoticiasPublicadas(): String {
        val collection: MongoCollection<Noticias> = database.getCollection("collNoticias", Noticias::class.java)

        var listaNoticias = ""
        collection.find().sort(Sorts.descending("fechaPublicacion")).limit(10).forEach {
            listaNoticias += "\n-----------------------------------ULTIMAS NOTICIAS--------------------------------------\n"
            listaNoticias += it.toString()
        }
        Logs.escribirLog("[correcto] fun listarLas10UltimasNoticiasPublicadas $listaNoticias")
        return listaNoticias
    }

    fun obtenerNoticia(nombre:String): Boolean {
        val collection: MongoCollection<Noticias> = database.getCollection("collNoticias", Noticias::class.java)
        val filtroHora = Filters.eq("titulo", nombre)
        if(collection.find(filtroHora).first() != null){
            Logs.escribirLog("[correcto] fun obtenerNoticia noticia obtenida: $nombre")
            return true
        }else{
            Logs.escribirLog("[incorrecto] fun obtenerNoticia  noticia no existe: $nombre")
            return false
        }
    }

    fun listarNoticiasPorEtiqueta(etiquetas: String): String {
        val collection: MongoCollection<Noticias> = database.getCollection("collNoticias", Noticias::class.java)
        val filtroTags = Filters.eq("tags", etiquetas)
        if(collection.find(filtroTags).first() != null){
            var listaNoticias = ""
            collection.find(filtroTags).forEach {
                listaNoticias += "\n-----------------------------------NOTICIAS DE $etiquetas--------------------------------------\n"
                listaNoticias += it.toString()
            }
            Logs.escribirLog("[correcto] fun listarNoticiasPorEtiqueta lista de noticias obtenida :$listaNoticias")
            return listaNoticias
        }else{
            Logs.escribirLog("[incorrecto] fun listarNoticiasPorEtiqueta la etiqueta no existe o no hay ninguna noticia asociada")
            return "esta etiqueta no ha publicado ninguna noticia"
        }
    }

    fun listarNoticiasPorUsuario(nombre: String): String {
        val collection: MongoCollection<Noticias> = database.getCollection("collNoticias", Noticias::class.java)
        val filtroUsuario = Filters.eq("autor", nombre)
        if(collection.find(filtroUsuario).first() != null){
            var listaNoticias = ""
            collection.find(filtroUsuario).forEach {
                listaNoticias += "\n-----------------------------------NOTICIA DE $nombre--------------------------------------\n"
                listaNoticias += it.toString()
            }
            Logs.escribirLog("[correcto] fun listarNoticiasPorUsuario lista de noticias obtenida :$listaNoticias")
            return listaNoticias
        }else{
            Logs.escribirLog("[incorrecto] fun listarNoticiasPorEtiqueta el usuario no existe o no hay ninguna noticia asociada")
            return "este usuario no ha publicado ninguna noticia"
        }
    }
}