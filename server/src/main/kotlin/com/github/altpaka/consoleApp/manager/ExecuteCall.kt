package com.github.altpaka.consoleApp.manager

import com.github.altpaka.consoleApp.commands.*
import com.github.altpaka.consoleApp.input.*
import com.github.altpaka.consoleApp.authentication.AuthenticationCommand
import com.github.altpaka.consoleApp.io.BufferLogger
import com.github.altpaka.consoleApp.sql.SQLAndMemoryCollection
import com.github.altpaka.consoleApp.users.User

fun executeCall(command: BoundCommand, logger: BufferLogger, collection: SQLAndMemoryCollection,
                user: User?){
    when (command) {
        is ApplicableToCollection -> if (user != null) {
            command.execute(logger, collection, user)
        }
        is SystemCommand -> command.execute(logger)
        is Help -> command.execute(logger, Add.Description,
            Clear, MaxByCoordinates,
            Exit,  Help, Show,
            Info, RemoveById.Description, RemoveFirst, RemoveGreater.Description,
            Update.Description)
        is AuthenticationCommand -> command.execute(logger, collection.getSqlCollection())
    }
}