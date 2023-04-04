package com.github.altpaka.consoleApp.threads

import com.github.altpaka.consoleApp.io.SocketWrap
import com.github.altpaka.consoleApp.serialize.OneLineAnswer

class RequestInOutputQueue(
    val answer: OneLineAnswer,
    val socketWrap: SocketWrap
)