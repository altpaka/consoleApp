package com.github.altpaka.consoleApp

import FileVerificationException
import com.github.altpaka.consoleApp.commands.AbstractDescription
import com.github.altpaka.consoleApp.commands.BoundCommand
import com.github.altpaka.consoleApp.io.FileStringReader
import com.github.altpaka.consoleApp.io.Logger
import java.io.File
import java.io.FileNotFoundException
import java.io.Serializable

class ExecuteScript(
    private val path: String
    ): BoundCommand, Serializable {
        fun execute(logger: Logger, client: Client){
            try {
                if (FileVerification.fullVerification(path)) {
                    HistoryOfExecutingScripts.addScript(logger, path)
                }
            } catch (ex: FileNotFoundException) {
                println(ex.message)
            } catch (ex: FileVerificationException) {
                println(ex.message)
            }
            logger.print("Выполнение скрипта: ${File(path)}")
            RequestManager.manage(
                logger,
                FileStringReader(path),
                client)
            HistoryOfExecutingScripts.removeScript()
        }

        companion object Description: AbstractDescription {
            override val title: String = "execute_script"
            override val help: String = "считать и исполнить скрипт из указанного файла"
        }
    }