package com.github.altpaka.consoleApp.input

import com.github.altpaka.consoleApp.commands.AbstractDescription
import com.github.altpaka.consoleApp.commands.AbstractPersonCollection
import com.github.altpaka.consoleApp.commands.ApplicableToCollection
import com.github.altpaka.consoleApp.io.Logger
import com.github.altpaka.consoleApp.users.User
import java.io.Serializable

class RemoveGreater(private val id: Long): ApplicableToCollection, Serializable {

    override fun execute(logger: Logger, collection: AbstractPersonCollection, user: User) {
        when (collection.removeGreater(id, user)) {
            AbstractPersonCollection.RemoveGreaterResult.EMPTY -> logger.print("Коллекция пуста, элементы c id превышающие $id не удалены")
            AbstractPersonCollection.RemoveGreaterResult.DELETED -> logger.print("Элементы c id превышающие $id успешно удалены")
            AbstractPersonCollection.RemoveGreaterResult.GREAT_NOT_FOUND -> logger.print("Элементы с id превышающие $id не найдены")
        }
    }

    companion object Description: AbstractDescription {
        override val title: String = "remove greater"
        override val help: String = "удалить из коллекции все элементы, превышающие заданный"
    }
}