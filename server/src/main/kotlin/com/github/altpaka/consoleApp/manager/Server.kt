package com.github.altpaka.consoleApp.manager

import com.github.altpaka.consoleApp.commands.AbstractPersonCollection
import com.github.altpaka.consoleApp.sql.SQLAndMemoryCollection
import com.github.altpaka.consoleApp.threads.*
import java.net.*
import java.nio.channels.ServerSocketChannel
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import java.util.logging.Logger

class Server(
    host: InetAddress,
    port: Int,
    log: Logger,
) {
    private val log: Logger
    private val serv: ServerSocketChannel = ServerSocketChannel.open()

    init {
        this.log = log
        val address: SocketAddress = InetSocketAddress(host, port)
        serv.bind(address)
        this.log.info("Установлено новое подключение")
    }

    fun mainLoop(collection: SQLAndMemoryCollection) {
        val inputQueue: BlockingQueue<RequestInInputQueue> = ArrayBlockingQueue(100)
        val outputQueue: BlockingQueue<RequestInOutputQueue> = ArrayBlockingQueue(100)

        val receiver = ReceiveManager(this.serv, inputQueue, log)
        val handler = HandleManager(inputQueue, outputQueue, collection)
        val sender = SendManager(outputQueue)

        val receiveThread = Thread(receiver)
        receiveThread.start()

        val handleThread = Thread(handler)
        handleThread.start()

        val sendThread = Thread(sender)
        sendThread.start()

        receiveThread.join()
        handleThread.join()
        sendThread.join()
    }
}