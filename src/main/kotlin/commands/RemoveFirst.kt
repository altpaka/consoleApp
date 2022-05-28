package commands

import com.github.Polina3116.lab5.AbstractPersonCollection

object RemoveFirst: ApplicableToCollection, AbstractDescription {
    override val title: String = "remove first"
    override val help: String = "удалить первый элемент из коллекции"

    override fun execute(logger: Logger, collection: AbstractPersonCollection) {
        if (collection.removeFirst()) {
            logger.print("Первый элемент удалён")
        } else {
            logger.print("Коллекция пуста, нет элементов для удаления")
        }
    }
}