package commands

import com.github.Polina3116.lab5.AbstractPersonCollection

class FilterContainsName(private val name: String): ApplicableToCollection {
    /**
     * ??????????????????????????????
     */
    override fun execute(logger: Logger, collection: AbstractPersonCollection) {
        val k = collection.filterContainsName(name)
        if (k.hasNext()) {
            for (i in k) {
                logger.print(i.toString())
            }
        } else {
            logger.print("Коллекция пуста")
        }
    }

    companion object Description: AbstractDescription{
        override val title: String = "filter contains name"
        override val help: String = "вывести элементы, значение поля name которых содержит заданную подстроку"
    }
}