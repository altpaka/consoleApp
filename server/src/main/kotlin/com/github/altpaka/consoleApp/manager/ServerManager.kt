package com.github.altpaka.consoleApp.manager

import com.github.altpaka.consoleApp.commands.AbstractPersonCollection
import com.github.altpaka.consoleApp.commands.AuthentificationPersonCollection
import com.github.altpaka.consoleApp.sql.SQLAndMemoryCollection
import java.net.BindException
import java.net.ConnectException
import java.net.InetAddress
import java.net.SocketException
import java.util.logging.Level
import java.util.logging.Logger

object ServerManager {
    fun manager(host: InetAddress, port: Int, collection: SQLAndMemoryCollection, log: Logger) {
        try {
            while (true) {
                val server = Server(host, port, log)
                try {
                    while (true) {
                        server.mainLoop(collection)
                    }
                } catch (ex: ConnectException) {
                    log.info("Завершена работа клиентского приложения")
                    log.log(Level.INFO, "ConnectException: ", ex)
                } catch (ex: SocketException) {
                    log.log(Level.SEVERE, "SocketException: ", ex)
                }
            }
        } catch (ex: BindException){
            log.log(Level.SEVERE, "BindException: ", ex)
        }
    }
}