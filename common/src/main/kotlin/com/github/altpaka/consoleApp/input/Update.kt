package com.github.altpaka.consoleApp.input

import com.github.altpaka.consoleApp.commands.AbstractDescription
import com.github.altpaka.consoleApp.commands.AbstractPersonCollection
import com.github.altpaka.consoleApp.commands.ApplicableToCollection
import com.github.altpaka.consoleApp.io.Logger
import com.github.altpaka.consoleApp.model.Person
import com.github.altpaka.consoleApp.users.User
import java.io.Serializable

class Update(private val id: Long, private val person: Person):
    ApplicableToCollection, Serializable {
    override fun execute(logger: Logger, collection: AbstractPersonCollection, user: User) {
        when (collection.update(id, person, user)) {
            AbstractPersonCollection.UpdateResult.EMPTY -> logger.print("Коллекция пуста, элемент НЕ был обновлён")
            AbstractPersonCollection.UpdateResult.UPDATED -> logger.print("Элемент успешно обновлён")
            AbstractPersonCollection.UpdateResult.NOT_FOUND -> logger.print("Элементов с соответствующим id не найдено")
        }
    }

    companion object Description: AbstractDescription {
        override val title: String = "update"
        override val help: String = "обновить значение элемента коллекции, id которого равен заданному"
    }
}