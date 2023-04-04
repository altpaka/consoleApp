package com.github.altpaka.consoleApp.commands

import com.github.altpaka.consoleApp.io.Logger

object ConsoleLogger: Logger {
    override fun print(message: String){
        println(message)
    }
}