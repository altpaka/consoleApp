package commands

import com.github.Polina3116.lab5.AbstractPersonCollection
import com.github.Polina3116.lab5.Person

class RemoveGreater(private val id: Int): ApplicableToCollection {

    override fun execute(logger: Logger, collection: AbstractPersonCollection) {
        when (collection.removeGreater(id)) {
            AbstractPersonCollection.RemoveGreaterResult.EMPTY -> logger.print("Коллекция пуста, элементы c id превышающие $id не удалены")
            AbstractPersonCollection.RemoveGreaterResult.DELETED -> logger.print("Элементы c id превышающие $id успешно удалены")
            AbstractPersonCollection.RemoveGreaterResult.NOT_FOUND -> logger.print("Элементы с id превышающие $id не найдены")
        }
    }

    companion object Description: AbstractDescription{
        override val title: String = "remove greater"
        override val help: String = "удалить из коллекции все элементы, превышающие заданный"
    }
}