package commands

import com.github.Polina3116.lab5.AbstractPersonCollection

interface ApplicableToCollection: BoundCommand {
    fun execute(logger: Logger, collection: AbstractPersonCollection)
}