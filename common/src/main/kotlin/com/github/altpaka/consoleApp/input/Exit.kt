package com.github.altpaka.consoleApp.input

import com.github.altpaka.consoleApp.commands.AbstractDescription
import com.github.altpaka.consoleApp.commands.BoundCommand
import java.io.Serializable

object Exit: BoundCommand,
    AbstractDescription, Serializable {
    override val title: String = "exit"
    override val help: String = "завершить программу (без сохранения в файл)"


}