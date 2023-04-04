package com.github.altpaka.consoleApp.io

abstract class AbstractStringReader: Iterable<String> {
    abstract fun hasNextLine(): Boolean

    abstract fun getNextLine(): String

    override operator fun iterator(): Iterator<String> = iterator{
        while (this@AbstractStringReader.hasNextLine()) {
            yield(this@AbstractStringReader.getNextLine())
        }
    }
}