package commands

import com.github.Polina3116.lab5.AbstractPersonCollection

object MaxByCoordinates: ApplicableToCollection, AbstractDescription {
    override val title: String = "max by coordinates"
    override val help: String = "вывести любой объект из коллекции, значение поля coordinates которого является максимальным"


    /**
     * ??????????????????????????????
     */

    override fun execute(logger: Logger, collection: AbstractPersonCollection) {
        if (collection.maxByCoordinates() == null){
            logger.print("Коллекция пуста")
        } else {
            logger.print(collection.maxByCoordinates().toString())
        }
    }
}