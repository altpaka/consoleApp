package com.github.altpaka.consoleApp.input

import com.github.altpaka.consoleApp.commands.AbstractDescription
import com.github.altpaka.consoleApp.commands.AbstractPersonCollection
import com.github.altpaka.consoleApp.commands.ApplicableToCollection
import com.github.altpaka.consoleApp.io.Logger
import com.github.altpaka.consoleApp.users.User
import java.io.Serializable

object RemoveFirst: ApplicableToCollection,
    AbstractDescription, Serializable {
    override val title: String = "remove first"
    override val help: String = "удалить первый элемент из коллекции"

    override fun execute(logger: Logger, collection: AbstractPersonCollection, user: User) {
        if (collection.removeFirst(user) == AbstractPersonCollection.RemoveFirstResult.DELETED) {
            logger.print("Первый элемент удалён")
        } else {
            logger.print("Коллекция пуста, нет элементов для удаления")
        }
    }
}