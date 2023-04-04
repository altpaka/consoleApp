package com.github.altpaka.consoleApp.authentication

import com.github.altpaka.consoleApp.commands.AuthentificationPersonCollection
import com.github.altpaka.consoleApp.commands.BoundCommand
import com.github.altpaka.consoleApp.io.BufferLogger

interface AuthenticationCommand: BoundCommand {
    fun execute(logger: BufferLogger, userCollection: AuthentificationPersonCollection)
}