package com.github.altpaka.consoleApp.input

import com.github.altpaka.consoleApp.commands.AbstractDescription
import com.github.altpaka.consoleApp.commands.AbstractPersonCollection
import com.github.altpaka.consoleApp.commands.ApplicableToCollection
import com.github.altpaka.consoleApp.io.Logger
import com.github.altpaka.consoleApp.users.User
import java.io.Serializable

class RemoveById(private val id: Long): ApplicableToCollection, Serializable {
    override fun execute(logger: Logger, collection: AbstractPersonCollection, user: User) {
        when (collection.removeById(id, user)) {
            AbstractPersonCollection.RemoveByIdResult.EMPTY -> logger.print("Коллекция пуста, элемент с id $id не удалён")
            AbstractPersonCollection.RemoveByIdResult.DELETED -> logger.print("Элемент c id $id успешно удалён")
            AbstractPersonCollection.RemoveByIdResult.NOT_FOUND -> logger.print("Элемент с id $id не найден")
        }
    }

    companion object Description: AbstractDescription {
        override val title: String = "remove by id"
        override val help: String = "удалить элемент из коллекции по его id"
    }
}