package com.github.altpaka.consoleApp.input

import com.github.altpaka.consoleApp.commands.AbstractDescription
import com.github.altpaka.consoleApp.commands.AbstractPersonCollection
import com.github.altpaka.consoleApp.commands.ApplicableToCollection
import com.github.altpaka.consoleApp.io.Logger
import com.github.altpaka.consoleApp.users.User
import java.io.Serializable

object MaxByCoordinates : ApplicableToCollection,
    AbstractDescription, Serializable {
    override val title: String = "max by coordinates"
    override val help: String = "вывести любой объект из коллекции, значение поля coordinates которого является максимальным"

    override fun execute(logger: Logger, collection: AbstractPersonCollection, user: User) {
        collection.maxByCoordinates(user).also { personMaxCoordinates ->
            if (personMaxCoordinates == null) {
                logger.print("Коллекция пуста")
            } else {
                logger.print(personMaxCoordinates.toString())
            }
        }

    }
}