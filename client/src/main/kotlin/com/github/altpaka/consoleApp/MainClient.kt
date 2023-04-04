package com.github.altpaka.consoleApp

import com.github.altpaka.consoleApp.commands.ConsoleLogger
import com.github.altpaka.consoleApp.io.ConsoleStringReader
import com.github.altpaka.consoleApp.io.Logger
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.net.ConnectException
import java.net.InetAddress
import java.util.logging.Level
import java.util.logging.LogManager

fun main() {
    val host = InetAddress.getLocalHost()
    val port = 5489

    val logger: Logger = ConsoleLogger
    val client: Client

    val log: java.util.logging.Logger = java.util.logging.Logger.getLogger("ClientLogger")
    try {
        LogManager.getLogManager().readConfiguration(
            FileInputStream("C:\\Users\\Polina\\IdeaProjects\\lab5\\client\\src\\main\\resources\\clientLog.properties")
//            FileInputStream("" + "")
        )
        log.info("Начало работы клиентского приложения")
    } catch (e: IOException){
        logger.print("Не удалось прочитать файл с настройками для логгирования: $e")
    } catch (e: ClassNotFoundException){
        logger.print("Логгер не настроен")
    } catch (e: FileNotFoundException){
        println(e.message)
    }

    try {
        client = Client(host, port, log)
    } catch (ex: ConnectException) {
        log.log(Level.SEVERE, "ConnectException: ", ex)
        logger.print("Ошибка соединения. Запустите сервер, потом клиент.")
        return
    }

    logger.print("Для начала работы введите команду 'registry' для регистрации или 'log_in' если вы уже зарегистрированы")
    RequestManager.manage(logger, ConsoleStringReader, client)
}