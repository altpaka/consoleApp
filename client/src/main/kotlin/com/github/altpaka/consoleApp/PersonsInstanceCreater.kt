package com.github.altpaka.consoleApp

import com.github.altpaka.consoleApp.model.Person
import com.github.altpaka.consoleApp.io.AbstractStringReader
import com.github.altpaka.consoleApp.io.Logger

/**
 * Enums contain the execution of the desired constructor
 */
enum class PersonsInstanceCreater(private val creator : (Logger, AbstractStringReader) -> Person?) : (Logger, AbstractStringReader) -> Person? {
    CREATE_FROM_FILE({logger, stringReader -> instanceCreate(
        stringReader.getNextLine(),
        stringReader.getNextLine(),
        stringReader.getNextLine(),
        stringReader.getNextLine(),
        stringReader.getNextLine(),
        stringReader.getNextLine(),
        stringReader.getNextLine(),
        stringReader.getNextLine(),
        stringReader.getNextLine(),
        stringReader.getNextLine(),
        1,
    )}),
    CREATE_WITH_INPUT({ logger, stringReader -> instanceCreate(3, logger)});

    override operator fun invoke(logger: Logger, stringReader: AbstractStringReader) = creator(logger, stringReader)
}