package com.github.altpaka.consoleApp.threads

import com.github.altpaka.consoleApp.io.SocketWrap
import com.github.altpaka.consoleApp.serialize.Request
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.ObjectInputStream
import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.channels.SocketChannel
import java.util.concurrent.BlockingQueue
import java.util.logging.Logger

class Receiver(
    private val sock: SocketChannel,
    private val inputQueue: BlockingQueue<RequestInInputQueue>,
    private val logger: Logger
): Runnable {
    var isRunning = false

    override fun run() {
        this.isRunning = true
        try {
            while (this.isRunning){
                val requestArr = ByteArray(1024 * 1024)
                this.read(requestArr)
                this.putCommand(requestArr)
            }
        } finally {
            this.isRunning = false
        }
    }

    private fun read(requestArr: ByteArray){
//        if (requestArr.contentEquals(ByteArray(1024 * 1024))) {
//            throw ConnectException()
//        }

        val requestBuf = ByteBuffer.wrap(requestArr)
        this.sock.read(requestBuf)
        (requestBuf as Buffer).flip()
    }

    private fun putCommand(requestArr: ByteArray) {
        val inputStream: InputStream = ByteArrayInputStream(requestArr)
        var request: Request
        try {
            ObjectInputStream(inputStream).use { ois ->
                request = ois.readObject() as Request
                logger.info(request.command.toString())
                this.inputQueue.put(RequestInInputQueue(request.command, request.user, SocketWrap(this.sock)))
            }
        } catch (ex: java.lang.Exception) {
            println(ex.message)
        }  catch (e: InterruptedException) {
            println("Поток чтения запросов прерван")
        } finally {
            inputStream.close()
        }
    }
}