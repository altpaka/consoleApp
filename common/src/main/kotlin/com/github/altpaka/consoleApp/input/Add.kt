package com.github.altpaka.consoleApp.input

import com.github.altpaka.consoleApp.commands.AbstractDescription
import com.github.altpaka.consoleApp.commands.AbstractPersonCollection
import com.github.altpaka.consoleApp.commands.ApplicableToCollection
import com.github.altpaka.consoleApp.io.Logger
import com.github.altpaka.consoleApp.model.Person
import com.github.altpaka.consoleApp.users.User
import java.io.Serializable

class Add(private val person: Person):
    ApplicableToCollection, Serializable {

    override fun execute(logger: Logger, collection: AbstractPersonCollection, user: User) {
        collection.add(person, user)
        logger.print("Элемент успешно добавлен в коллекцию")
    }

    companion object Description: AbstractDescription {
        override val title: String = "add"
        override val help: String = "добавить новый элемент в коллекцию"
    }
}