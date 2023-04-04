package com.github.altpaka.consoleApp.threads

import com.github.altpaka.consoleApp.commands.BoundCommand
import com.github.altpaka.consoleApp.io.SocketWrap
import com.github.altpaka.consoleApp.users.User

class RequestInInputQueue(
    val command: BoundCommand,
    val user: User? = null,
    val socketWrap: SocketWrap
)