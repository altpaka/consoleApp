package commands

import com.github.Polina3116.lab5.AbstractPersonCollection
import com.github.Polina3116.lab5.Person

class Add(private val person: Person): ApplicableToCollection {

    override fun execute(logger: Logger, collection: AbstractPersonCollection) {
        collection.add(person)
        logger.print("Элемент успешно добавлен в коллекцию")
    }

    companion object Description: AbstractDescription{
        override val title: String = "add"
        override val help: String = "добавить новый элемент в коллекцию"
    }
}