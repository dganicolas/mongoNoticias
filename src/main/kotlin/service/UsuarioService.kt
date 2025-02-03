package org.example.service

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import org.example.model.Usuarios
import org.example.utils.Logs
import org.example.utils.SesionIniciada

/*
• Los usuarios registrados pueden publicar noticias
• Los usuarios pueden escribir comentarios (a no ser que estén en
estado inactivo/banned)
• Registrar usuarios.
• El email del usuario y el nick deben ser únicos
* */
class UsuarioService(private val database:MongoDatabase) {

    fun registrarUsuario(usuario: Usuarios): String {
        val collection: MongoCollection<Usuarios> = database.getCollection("collUsuarios", Usuarios::class.java)
        val filtroEmailOUsuario =
            Filters.or(Filters.eq("nombre", usuario.nombre), Filters.eq("email", usuario.email))

        if(collection.find(filtroEmailOUsuario).firstOrNull() == null){
            Logs.escribirLog("[correcto] fun registrarUsuario usuario registrado correctamente" +collection.insertOne(usuario).toString())
            SesionIniciada.usuarioActivo = usuario.nombre
            return "usuario registrado correctamente"
        }else{
            Logs.escribirLog("[incorrecto] fun registrarUsuario Ese email o nick ya existe en la plataforma")
            return "Ese email ya existe en la plataforma"
        }
    }

    fun iniciarSesion(emailOUsuario:String): String {
        val collection: MongoCollection<Usuarios> = database.getCollection("collUsuarios", Usuarios::class.java)
        val filtroEmailOUsuario =
            Filters.or(Filters.eq("nombre", emailOUsuario), Filters.eq("email", emailOUsuario))
        val usuario = collection.find(filtroEmailOUsuario).first()

        if(usuario == null){
            Logs.escribirLog("[incorrecto] sesion no iniciada no existe en la bbdd")
            return "ese usuario no existe"
        }else{
            SesionIniciada.usuarioActivo = usuario.nombre
            if(!usuario.estado){
                Logs.escribirLog("[incorrecto] fun iniciarSesion sesion no iniciada con usuario ${usuario.email} por baneo")
                return "usuario baneado, lo siento, create una cuenta nueva"
            }
            Logs.escribirLog("[correcto] fun iniciarSesion sesion iniciada con usuario ${usuario.email}")
            return "bienvenido señor: ${usuario.nombre}"
        }
    }
}