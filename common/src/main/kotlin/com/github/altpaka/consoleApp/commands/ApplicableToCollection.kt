package com.github.altpaka.consoleApp.commands

import com.github.altpaka.consoleApp.io.Logger
import com.github.altpaka.consoleApp.users.User

interface ApplicableToCollection: com.github.altpaka.consoleApp.commands.BoundCommand {
    fun execute(logger: Logger, collection: com.github.altpaka.consoleApp.commands.AbstractPersonCollection, user: User)
}