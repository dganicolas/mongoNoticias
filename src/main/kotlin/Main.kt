package org.example

import org.example.model.Comentarios
import org.example.model.Direccion
import org.example.model.Noticias
import org.example.model.Usuarios
import org.example.service.ComentariosService
import org.example.service.NoticiasService
import org.example.service.UsuarioService
import org.example.utils.ConexionMongo
import org.example.utils.Logs
import org.example.utils.SesionIniciada
import java.util.*

fun main() {
    var activo = true
    val database = ConexionMongo.getDatabase("adatPrueba")
    val usuarioService = UsuarioService(database)
    val noticiasService = NoticiasService(database)
    val comentariosService = ComentariosService(database)
    do{
        if(SesionIniciada.usuarioActivo != ""){
            println("bienvenido a Twitter Y")
            println("que quiere hacer")
            println("1 Los usuarios registrados pueden publicar noticias \n" +
                    "2 Los usuarios pueden escribir comentarios (a no ser que estén en estado inactivo/banned) \n" +
                    "3 Registrar usuarios. \n" +
                    "4 Publicar noticias. \n" +
                    "5 Listar las noticias publicadas por un usuario. \n" +
                    "6 Listar los comentarios de una noticia. \n" +
                    "7 Buscar noticias por etiquetas. \n" +
                    "8 Listar las 10 últimas noticias publicadas\n" +
                    "9 salir del programa")
            val opcion = readln()
            when (opcion) {
                "1" -> {
                    //1 Los usuarios registrados pueden publicar noticias
                    println("Dime el título de la noticia:")
                    val titulo = readln()

                    println("Escribe el cuerpo de la noticia:")
                    val cuerpo = readln()

                    println("Ingresa las etiquetas (separadas por comas):")
                    val tags = readln().split(",").map { it.trim() }

                    val fechaPublicacion = Date()

                    val noticia = Noticias(
                        titulo = titulo,
                        cuerpo = cuerpo,
                        fechaPublicacion = fechaPublicacion,
                        autor = SesionIniciada.usuarioActivo,
                        tags = tags
                    )

                    noticiasService.publicarNoticia(noticia)
                    println("Noticia publicada con éxito.")
                }
                "2" -> {
                    //2 Los usuarios pueden escribir comentarios (a no ser que estén en estado inactivo/banned)
                    println("Ingresa el título de la noticia en la que comentar:")
                    val noticia = readln()

                    println("Escribe tu comentario:")
                    val comentario = readln()

                    val fechaHora = Date() // Se usa la fecha y hora actual

                    val nuevoComentario = Comentarios(
                        autor = SesionIniciada.usuarioActivo,
                        noticia = noticia,
                        comentario = comentario,
                        fechaHora = fechaHora
                    )
                    if(noticiasService.obtenerNoticia(noticia)){
                        comentariosService.escribirComentarios(nuevoComentario)
                        println("Comentario publicado con éxito.")
                    }else{
                        Logs.escribirLog("[Incorrecto] fun escribirComentarios : el nombre de la noticia no existe")
                        println("esa noticia no existe")
                    }

                }
                "3" -> {
                    //3 Registrar usuarios.
                    println("Ingresa el nombre de usuario:")
                    val nombre = readln()

                    println("Ingresa el correo electrónico:")
                    val email = readln()

                    println("Ingresa la calle:")
                    val calle = readln()

                    println("Número de la casa:")
                    val num = readln()

                    println("Puerta:")
                    val puerta = readln()

                    println("Código postal:")
                    val cp = readln()

                    println("Ciudad:")
                    val ciudad = readln()

                    println("Ingresa los números de teléfono (separados por comas):")
                    val telefonos = readln().split(",").map { it.trim() }.toMutableList()

                    val direccion = Direccion(
                        calle = calle,
                        num = num,
                        puerta = puerta,
                        cp = cp,
                        ciudad = ciudad
                    )

                    val nuevoUsuario = Usuarios(
                        email = email,
                        nombre = nombre,
                        estado = true,
                        direccionPostal = direccion,
                        telefonos = telefonos
                    )
                    println(usuarioService.registrarUsuario(nuevoUsuario))
                }

                "4" -> {
                    //4 Publicar noticias.
                    println("Dime el título de la noticia:")
                    val titulo = readln()

                    println("Escribe el cuerpo de la noticia:")
                    val cuerpo = readln()

                    println("Ingresa las etiquetas (separadas por comas):")
                    val tags = readln().split(",").map { it.trim() }

                    println("Ingresa el nombre del autor:")
                    val autor = readln()

                    val noticia = Noticias(
                        titulo = titulo,
                        cuerpo = cuerpo,
                        fechaPublicacion = Date(),
                        autor = autor,
                        tags = tags
                    )
                    println(noticiasService.publicarNoticia(noticia))
                }
                "5" -> {
                    //5 Listar las noticias publicadas por un usuario.
                    println("Ingresa el nombre del usuario del que quieres ver las noticias publicadas:")
                    val nombreUsuario = readln()
                    println(noticiasService.listarNoticiasPorUsuario(nombreUsuario))
                }
                "6" -> {
                    println("Ingresa el nombre de la noticia que quieres ver los comentarios publicados:")
                    val titulo = readln()
                    println(comentariosService.listarComentarios(titulo))
                }
                "7" -> {
                    println("Ingresa la etiqueta que quieres ver las noticias publicadas:")
                    val nombreUsuario = readln()
                    println(noticiasService.listarNoticiasPorEtiqueta(nombreUsuario))
                }
                "8" -> {
                    println(noticiasService.listarLas10UltimasNoticiasPublicadas())
                }
                "9" -> {
                    activo = false
                    Logs.escribirLog("[correcto] cerrando conexion y sesion del ususar ${SesionIniciada.usuarioActivo}")
                }
                else -> {
                    println("opcion desconocida")
                    Logs.escribirLog("[incorrecto] accion desconocida por parte del usuario ${SesionIniciada.usuarioActivo}")
                }
            }
        }else{
            println("porfavor inicia sesion??")
            println("dime tu email(inventatelo)")
            val usuario = readln()
            println(usuarioService.registrarUsuario(Usuarios(usuario,usuario,true, Direccion("XD","2","atras","palo","xd"), mutableListOf())))
        }



    }while(SesionIniciada.usuarioActivo != "" || activo)
    ConexionMongo.close()
}