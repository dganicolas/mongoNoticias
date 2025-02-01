package org.example.service

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import org.example.model.Usuarios
import org.example.utils.SesionIniciada

/*
• Los usuarios registrados pueden publicar noticias
• Los usuarios pueden escribir comentarios (a no ser que estén en
estado inactivo/banned)
• Registrar usuarios.
• El email del usuario y el nick deben ser únicos
* */
class UsuarioService(val database:MongoDatabase) {

    fun registrarUsuario(usuario: Usuarios): String {
        val collection: MongoCollection<Usuarios> = database.getCollection("coll_usuarios", Usuarios::class.java)
        val filtroEmailOUsuario =
            Filters.or(Filters.eq("nombre", usuario.nombre), Filters.eq("email", usuario.email))

        if(collection.find(filtroEmailOUsuario).first() == null){
            return collection.insertOne(usuario).toString()
        }else{
            return "Ese email yas existe en la plataforma"
        }
    }

    fun iniciarSesion(emailOUsuario:String): String {
        val collection: MongoCollection<Usuarios> = database.getCollection("coll_usuarios", Usuarios::class.java)
        val filtroEmailOUsuario =
            Filters.or(Filters.eq("nombre", emailOUsuario), Filters.eq("email", emailOUsuario))
        val usuario = collection.find(filtroEmailOUsuario).first()

        if(usuario == null){
            return "ese usuario no existe"
        }else{
            SesionIniciada.usuarioActivo = usuario.nombre
            return "bienvenido señor: ${usuario.nombre}"
        }
    }
}