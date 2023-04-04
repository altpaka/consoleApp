package com.github.altpaka.consoleApp.input

import com.github.altpaka.consoleApp.commands.AbstractDescription
import com.github.altpaka.consoleApp.commands.AbstractPersonCollection
import com.github.altpaka.consoleApp.commands.ApplicableToCollection
import com.github.altpaka.consoleApp.io.Logger
import com.github.altpaka.consoleApp.users.User
import java.io.Serializable

object Show: ApplicableToCollection,
    AbstractDescription, Serializable {
    override val title: String = "show"
    override val help: String = "вывести все элементы коллекции в строковом представлении"

    override fun execute(logger: Logger, collection: AbstractPersonCollection, user: User) {
        if (collection.iterator().hasNext()) {
            for (i in collection) {
                logger.print(i.toString())
            }
        } else {
            logger.print("Коллекция пуста")
        }
    }
}