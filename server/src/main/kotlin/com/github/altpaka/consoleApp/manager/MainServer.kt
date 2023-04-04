package com.github.altpaka.consoleApp.manager

import com.github.altpaka.consoleApp.sql.SQLAndMemoryCollection
import com.github.altpaka.consoleApp.sql.SQLManager
import org.postgresql.util.PSQLException
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.Logger

fun main() {
    try {
        val host = InetAddress.getLocalHost()
        val port = 5489

        val log = Logger.getLogger("ServerLogger")
        try {
//            LogManager.getLogManager().readConfiguration(FileInputStream("./servLog.properties"))
            LogManager.getLogManager().readConfiguration(FileInputStream("C:\\Users\\Polina\\IdeaProjects\\"+
                    "lab5\\server\\src\\main\\resources\\servLog.properties"))
            log.info("Начало работы серверного приложения")

        } catch (e: IOException) {
            println("Could not setup logger configuration: $e")
        } catch (ex: ClassNotFoundException) {
            println("Logger not configured")
        } catch (ex: FileNotFoundException){
            println("Config file not found")
        }

        try {
            val collection = SQLAndMemoryCollection()
            SQLManager.main(collection, log)
            ServerManager.manager(host, port, collection, log)
        } catch (ex: PSQLException) {
            log.log(Level.WARNING, "PSQLException ", ex)
            println("Ошибка инициализации БД")
        } catch (ex: FileNotFoundException) {
            log.log(Level.WARNING, "FileNotFoundException: ", ex)
        } catch (e: FileNotFoundException) {
            println("Не найден файл db.txt для аутенфикации")
            System.exit(1)
        }
        return
    } catch (ex: UnknownHostException) {
        println(ex.message)
        return
    }
}