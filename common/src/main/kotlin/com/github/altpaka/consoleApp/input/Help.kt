package com.github.altpaka.consoleApp.input

import com.github.altpaka.consoleApp.commands.AbstractDescription
import com.github.altpaka.consoleApp.commands.BoundCommand
import com.github.altpaka.consoleApp.io.Logger
import java.io.Serializable

object Help: BoundCommand,
    AbstractDescription, Serializable {
    override val title: String = "help"
    override val help: String = "вывести справку по доступным командам"

    fun execute(logger: Logger, vararg descriptions: AbstractDescription) {
        for (i in descriptions.toMutableSet().apply { add(Help) }){
            logger.print("${i.title} - ${i.help}")
        }
    }
}