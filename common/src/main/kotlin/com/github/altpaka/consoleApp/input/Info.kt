package com.github.altpaka.consoleApp.input

import com.github.altpaka.consoleApp.commands.AbstractDescription
import com.github.altpaka.consoleApp.commands.AbstractPersonCollection
import com.github.altpaka.consoleApp.commands.ApplicableToCollection
import com.github.altpaka.consoleApp.io.Logger
import com.github.altpaka.consoleApp.users.User
import java.io.Serializable

object Info: ApplicableToCollection,
    AbstractDescription, Serializable {
    override val title: String = "info"
    override val help: String = "вывести информацию о коллекции"

    override fun execute(logger: Logger, collection: AbstractPersonCollection, user: User) {
        logger.print(collection.info(user).typeOfCollection)
        logger.print(collection.info(user).initDate.toString())
        logger.print(collection.info(user).elemCount.toString())
    }
}