package commands

import com.github.Polina3116.lab5.AbstractPersonCollection

object Clear:  ApplicableToCollection, AbstractDescription {
    override val title: String = "clear"
    override val help: String = "очистить коллекцию"

    override fun execute(logger: Logger, collection: AbstractPersonCollection) {
        collection.clear()
        logger.print("Коллекция очищена")
    }
}