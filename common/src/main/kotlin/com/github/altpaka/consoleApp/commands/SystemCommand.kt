package com.github.altpaka.consoleApp.commands

import com.github.altpaka.consoleApp.io.Logger

interface SystemCommand: BoundCommand {
    fun execute(logger: Logger)
}