package commands

import com.github.Polina3116.lab5.AbstractPersonCollection
import com.github.Polina3116.lab5.Person

class Update(private val id: Int, private val person: Person): ApplicableToCollection {
    override fun execute(logger: Logger, collection: AbstractPersonCollection) {
        when (collection.update(id, person)) {
            AbstractPersonCollection.UpdateResult.EMPTY -> logger.print("Коллекция пуста, элемент НЕ был обновлён")
            AbstractPersonCollection.UpdateResult.UPDATED -> logger.print("Элемент успешно обновлён")
            AbstractPersonCollection.UpdateResult.NOT_FOUND -> logger.print("Элементов с соответствующим id не найдено")
        }
    }

    companion object Description: AbstractDescription{
        override val title: String = "update"
        override val help: String = "обновить значение элемента коллекции, id которого равен заданному"
    }
}