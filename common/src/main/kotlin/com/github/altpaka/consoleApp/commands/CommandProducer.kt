package com.github.altpaka.consoleApp.commands

interface CommandProducer {
    fun getNextCommand(): com.github.altpaka.consoleApp.commands.BoundCommand?
}