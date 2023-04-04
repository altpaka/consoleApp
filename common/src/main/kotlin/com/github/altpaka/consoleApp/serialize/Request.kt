package com.github.altpaka.consoleApp.serialize

import com.github.altpaka.consoleApp.commands.BoundCommand
import com.github.altpaka.consoleApp.users.User
import java.io.Serializable

class Request(
    val command: BoundCommand,
    val user: User? = null
): Serializable