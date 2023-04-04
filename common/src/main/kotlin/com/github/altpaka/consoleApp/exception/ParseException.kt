package com.github.altpaka.consoleApp.exception

import java.lang.Exception

class ParseException : Exception(){
    companion object {
        const val message: String = "Аргументы для команды не были получены, введите команду заново."
    }
}