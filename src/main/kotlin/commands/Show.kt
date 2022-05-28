package commands

import com.github.Polina3116.lab5.AbstractPersonCollection

object Show: ApplicableToCollection, AbstractDescription {
    override val title: String = "show"
    override val help: String = "вывести все элементы коллекции в строковом представлении"

    override fun execute(logger: Logger, collection: AbstractPersonCollection) {
        if (collection.iterator().hasNext()) {
            for (i in collection) {
                logger.print(i.toString())
            }
        } else {
            logger.print("Коллекция пуста")
        }
    }
}