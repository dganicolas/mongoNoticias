package org.example.utils

import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object Logs {
    private val logFile = File("src/main/resources/logs.txt")

    init {
        if (logFile.exists()) {
            logFile.createNewFile()
        }
    }

    fun escribirLog(mensaje: String) {
        logFile.appendText("${SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Date())} - $mensaje\n")
    }
}
