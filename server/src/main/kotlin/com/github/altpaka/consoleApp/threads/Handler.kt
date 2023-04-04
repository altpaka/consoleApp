package com.github.altpaka.consoleApp.threads

import com.github.altpaka.consoleApp.commands.AbstractPersonCollection
import com.github.altpaka.consoleApp.commands.AuthentificationPersonCollection
import com.github.altpaka.consoleApp.exception.CollectionException
import com.github.altpaka.consoleApp.io.BufferLogger
import com.github.altpaka.consoleApp.manager.executeCall
import com.github.altpaka.consoleApp.sql.SQLAndMemoryCollection
import java.sql.SQLException
import java.util.concurrent.BlockingQueue

class Handler(
    private var inputQueue: BlockingQueue<RequestInInputQueue>,
    private var outputQueue: BlockingQueue<RequestInOutputQueue>,
    private val collection: SQLAndMemoryCollection
): Runnable {

    override fun run() {
        if (this.inputQueue.isNotEmpty()) {
            val requestInQueue = inputQueue.take()
            println("Извлечён запрос из входящей очереди")
            val bufferLogger = BufferLogger(requestInQueue.socketWrap)
            try {
                executeCall(requestInQueue.command, bufferLogger, collection, requestInQueue.user)
            } catch (ex: CollectionException) {
                bufferLogger.print(ex.message)
            } catch (ex: SQLException) {
                bufferLogger.print("Ошибка обращения к базе данных")
            }
            bufferLogger.build()
            println(bufferLogger.answer.result)
            try {
                this.outputQueue.put(RequestInOutputQueue(bufferLogger.answer, bufferLogger.socketWrap))
            } catch (e: InterruptedException){
                Thread.currentThread().interrupt()
            }
            println("Запрос отправлен в исходящую очередь")
        }
    }
}