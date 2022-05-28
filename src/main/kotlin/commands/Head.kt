package commands

import com.github.Polina3116.lab5.AbstractPersonCollection

object Head:  AbstractDescription, ApplicableToCollection{
    override val title: String = "head"
    override val help: String = "вывести первый элемент коллекции"

    override fun execute(logger: Logger, collection: AbstractPersonCollection) {
        logger.print(collection.first().toString())
    }
}