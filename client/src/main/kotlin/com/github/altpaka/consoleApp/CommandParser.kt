package com.github.altpaka.consoleApp

import com.github.altpaka.consoleApp.authentication.LogIn
import com.github.altpaka.consoleApp.authentication.Register
import com.github.altpaka.consoleApp.commands.*
import com.github.altpaka.consoleApp.input.*
import com.github.altpaka.consoleApp.exception.ParseException
import com.github.altpaka.consoleApp.exception.UnexpectedCommandException
import com.github.altpaka.consoleApp.io.AbstractStringReader
import com.github.altpaka.consoleApp.io.FileStringReader
import com.github.altpaka.consoleApp.io.Logger
import com.github.altpaka.consoleApp.users.User
import kotlin.jvm.Throws

/**
 * Abstract class for any parser
 */
object CommandParser{
    /**
     * return Pair<Command, Array<String>>? where Command is name and Array<String>>? is nullable attributes
     */
    @JvmStatic
    @Throws(UnexpectedCommandException::class, ParseException::class)
    fun parse(str: String, stringReader: AbstractStringReader, logger: Logger): BoundCommand {
        val request = str.trim().split(Regex("""\s+"""))
        if (request.isEmpty()){
            throw UnexpectedCommandException()
        }

        val personsInstanceCreater: PersonsInstanceCreater = if (stringReader is FileStringReader) {
            PersonsInstanceCreater.CREATE_FROM_FILE
        } else {
            PersonsInstanceCreater.CREATE_WITH_INPUT
        }

        val command: BoundCommand = when (request[0]) {
            "registry" -> Register(
                requestLogin(3, logger),
                requestPassword(3, logger)
            )
            "log_in" -> LogIn(
                requestLogin(3, logger),
                requestPassword(3, logger)
            )
            "add" -> Add(
                personsInstanceCreater.invoke(logger, stringReader) ?: throw ParseException()
            )
            "clear" -> Clear
            "execute_script" -> ExecuteScript()
            "exit" -> Exit
            "help" -> Help
            "info" -> Info
            "remove_by_id" -> RemoveById(request[1].toLong())
            "remove_first" -> RemoveFirst
            "show" -> Show
            "update" -> Update(
                request[1].toLong(),
                personsInstanceCreater.invoke(logger, stringReader) ?: throw ParseException()
            )
            "max_by_coordinates" -> MaxByCoordinates
            "remove_greater" -> RemoveGreater(request[1].toLong())

            else -> throw UnexpectedCommandException()

        }
        return command
    }
}