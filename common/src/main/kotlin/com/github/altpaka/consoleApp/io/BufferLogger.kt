package com.github.altpaka.consoleApp.io

import com.github.altpaka.consoleApp.serialize.OneLineAnswer
import com.github.altpaka.consoleApp.users.User
import com.github.altpaka.consoleApp.io.Logger

class BufferLogger(
    val socketWrap: SocketWrap
) : Logger {
    private var user: User? = null
    private var buf = StringBuilder()
    var answer: OneLineAnswer = OneLineAnswer(this.user, this.buf.toString())

    override fun print(message: String) {
        if (buf.isNotBlank()) {
            this.buf.append("\n$message")
        } else {
            this.buf.append(message)
        }
    }

    fun setUser(user: User){
        this.user = user
    }

    fun build() {
        answer = OneLineAnswer(this.user, this.buf.toString())
    }
}