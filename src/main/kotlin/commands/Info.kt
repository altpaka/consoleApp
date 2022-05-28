package commands

import com.github.Polina3116.lab5.AbstractPersonCollection

object Info: ApplicableToCollection, AbstractDescription {
    override val title: String = "info"
    override val help: String = "вывести информацию о коллекции"

    override fun execute(logger: Logger, collection: AbstractPersonCollection) {
        logger.print(collection.info().typeOfCollection)
        logger.print(collection.info().initDate.toString())
        logger.print(collection.info().elemCount.toString())
    }
}