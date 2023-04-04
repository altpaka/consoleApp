package com.github.altpaka.consoleApp

import com.github.altpaka.consoleApp.serialize.OneLineAnswer
import com.github.altpaka.consoleApp.serialize.Request
import java.io.InputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.OutputStream
import java.net.InetAddress
import java.net.Socket


class Client(
    private val host: InetAddress,
    private val port: Int,
    private val log: java.util.logging.Logger
){
    val sock: Socket = Socket(this.host, this.port)
    private val arr: ByteArray = ByteArray(1024*1024)

    fun send(request: Request) {
        val os: OutputStream = sock.getOutputStream()
        try {
            ObjectOutputStream(os).use { oos ->
                oos.writeObject(request)
            }
        } catch (ex: Exception) {
            println(ex.message)
        } finally {
            os.close()
        }

        this.log.info("Отправлен новый запрос на сервер")
    }

    fun recieve(): OneLineAnswer {
        val inputStream: InputStream = sock.getInputStream()
        var oneLineAnswer: OneLineAnswer = OneLineAnswer(null, "Запрос не выполнен")
        try {
            ObjectInputStream(inputStream).use { ois ->
                oneLineAnswer = ois.readObject() as OneLineAnswer
            }
        } catch (ex: java.lang.Exception) {
            println(ex.message)
        } finally {
            inputStream.close()
        }
        this.log.info("Получен ответ от сервера")
        return oneLineAnswer
    }

    fun getArr(): ByteArray{
        return this.arr
    }
}