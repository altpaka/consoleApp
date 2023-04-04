package com.github.altpaka.consoleApp.io

import com.github.altpaka.consoleApp.serialize.OneLineAnswer
import java.io.ObjectOutputStream
import java.io.OutputStream
import java.nio.channels.Channels
import java.nio.channels.SocketChannel

class SocketWrap (
    private val sock: SocketChannel,
) {
    fun sendToSocket(answer: OneLineAnswer){
        val os: OutputStream = Channels.newOutputStream(this.sock)
        try {
            ObjectOutputStream(os).use { oos ->
                oos.writeObject(answer)
            }
        } catch (ex: Exception) {
            println(ex.message)
        } finally {
            os.close()
        }
    }
}