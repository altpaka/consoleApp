package commands

import com.github.Polina3116.lab5.AbstractPersonCollection

class RemoveById(private val id: Int): ApplicableToCollection {
    override fun execute(logger: Logger, collection: AbstractPersonCollection) {
        when (collection.removeById(id)) {
            AbstractPersonCollection.RemoveByIdResult.EMPTY -> logger.print("Коллекция пуста, элемент с id $id не удалён")
            AbstractPersonCollection.RemoveByIdResult.DELETED -> logger.print("Элемент c id $id успешно удалён")
            AbstractPersonCollection.RemoveByIdResult.NOT_FOUND -> logger.print("Элемент с id $id не найден")
        }
    }

    companion object Description: AbstractDescription{
        override val title: String = "remove by id"
        override val help: String = "удалить элемент из коллекции по его id"
    }
}