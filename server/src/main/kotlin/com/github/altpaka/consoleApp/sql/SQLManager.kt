package com.github.altpaka.consoleApp.sql

import java.util.logging.Logger

object SQLManager {
    fun main(collection: SQLAndMemoryCollection, log: Logger) {

        for (i in collection.getSqlCollection()){
            collection.getCollectionInMemory().copyFromDB(i)
        }
        log.info("База данных и коллекция в памяти синхронизированы")

    }
}