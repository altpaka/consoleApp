package com.github.altpaka.consoleApp.collection

import com.github.altpaka.consoleApp.commands.AbstractPersonCollection
import com.github.altpaka.consoleApp.model.Person
import com.github.altpaka.consoleApp.users.User
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.time.LocalDate
import java.util.*

class CollectionInMemory : AbstractPersonCollection {

    private val collection: MutableList<Person> = mutableListOf()
    private val initDate: LocalDate = LocalDate.now()

//    private val passportIDset: Set<String>
    @Synchronized
    fun copyFromDB(person: Person){
        this.collection.add(person)
    }

    @Synchronized
    override fun add(person: Person, user: User): Long {
        person.user = user.login
        this.collection.add(person)
        return person.id ?: throw NullPointerException()
    }

//    @Synchronized
//    override fun addIfMin(name: String, person: Person, user: User): Pair<AbstractPersonCollection.AddIfMinResult, Int?> {
//        return if (this.collection.size != 0) {
//            val minElem = this.collection.sortedWith(compareBy { it.name.length })[0]
//            if (minElem.compareTo(name) > 0) {
//                Pair(AbstractPersonCollection.AddIfMinResult.SUCCESS, add(person, user))
//            } else {
//                Pair(AbstractPersonCollection.AddIfMinResult.LESS_FOUND, null)
//            }
//        } else {
//            Pair(AbstractPersonCollection.AddIfMinResult.EMPTY, add(person, user))
//        }
//    }

    @Synchronized
    override fun clear(user: User): AbstractPersonCollection.ClearResult {
        if (this.collection.any { it.user == user.login}) {
            this.collection.removeIf { elem ->
                elem.user == user.login
            }
            return AbstractPersonCollection.ClearResult.DELETED
        }
        return AbstractPersonCollection.ClearResult.NOT_FOUND
    }

    @Synchronized
    override fun info(user: User): AbstractPersonCollection.Information {
        return AbstractPersonCollection.Information(this.collection.size, this.initDate)
    }

    @Synchronized
    override fun removeById(id: Long, user: User): AbstractPersonCollection.RemoveByIdResult {
        if (collection.size == 0) {
            return AbstractPersonCollection.RemoveByIdResult.EMPTY
        } else if (collection.removeIf {
                it.id == id && it.user == user.login
            }) {
            return AbstractPersonCollection.RemoveByIdResult.DELETED
        }
        return AbstractPersonCollection.RemoveByIdResult.NOT_FOUND
    }

    @Synchronized
    override fun removeFirst(user: User): AbstractPersonCollection.RemoveFirstResult {
        return if (this.collection.size > 0) {
            if (this.collection.first().user == user.login) {
                this.collection.removeFirst()
            }
            this.collection.removeFirst()
            AbstractPersonCollection.RemoveFirstResult.DELETED
        } else {
            AbstractPersonCollection.RemoveFirstResult.NOT_FOUND
        }
    }

    @Synchronized
    override fun removeGreater(id: Long, user: User): AbstractPersonCollection.RemoveGreaterResult {
        return if (this.collection.size != 0) {
            if (this.collection.any { it.id!! > id && it.user == user.login}) {
                this.collection.removeIf { elem ->
                    elem.id!! > id && elem.user == user.login
                }
                AbstractPersonCollection.RemoveGreaterResult.DELETED
            } else {
                AbstractPersonCollection.RemoveGreaterResult.GREAT_NOT_FOUND
            }
        } else {
            AbstractPersonCollection.RemoveGreaterResult.EMPTY
        }
    }

    @Synchronized
    override fun iterator(): Iterator<Person> {
        return this.collection.iterator()
    }

    @Synchronized
    override fun maxByCoordinates(user: User): Long {
        var max: Long = (0).toLong()
        for (elem in this.collection) {
            if (elem.coordinates.x > max) { max = elem.coordinates.x }
        }
        return max.toLong()
    }

    @Synchronized
    override fun update(id: Long, person: Person, user: User): AbstractPersonCollection.UpdateResult {
        return if (this.collection.size > 0) {
            if (this.collection.none { it.id == id && it.user == user.login}) {
                AbstractPersonCollection.UpdateResult.NOT_FOUND
            } else {
                for (elem in collection) {
                    if (elem.id == id && elem.user == user.login) {
                        collection[collection.indexOf(elem)] = person
                    }
                }
                AbstractPersonCollection.UpdateResult.UPDATED
            }
        } else {
            AbstractPersonCollection.UpdateResult.EMPTY
        }
    }
}