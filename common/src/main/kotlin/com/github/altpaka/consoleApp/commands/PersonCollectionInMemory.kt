package com.github.altpaka.consoleApp.commands


import com.github.altpaka.consoleApp.model.Person
import com.github.altpaka.consoleApp.users.User
import kotlinx.datetime.Clock
import java.time.LocalDate
import java.util.*
import kotlin.math.abs

class PersonCollectionInMemory: AbstractPersonCollection {

    private val collection: LinkedList<Person> = LinkedList()

    override fun info(user: User): AbstractPersonCollection.Information {
        return AbstractPersonCollection.Information(this.collection.size, LocalDate.now())
    }

    override fun add(person: Person, user: User): Long {
        this.collection.add(person)
        return person.id!!
    }

    override fun update(id: Long, person: Person, user: User): AbstractPersonCollection.UpdateResult {
        return if (this.collection.size > 0) {
            if (this.collection.none { it.id == id }) {
                AbstractPersonCollection.UpdateResult.NOT_FOUND
            } else {
                for (elem in collection) {
                    if (elem.id == id) {
                        collection[collection.indexOf(elem)] = person
                    }
                }
                AbstractPersonCollection.UpdateResult.UPDATED
            }
        } else {
            AbstractPersonCollection.UpdateResult.EMPTY
        }
    }

    override fun removeById(id: Long, user: User): AbstractPersonCollection.RemoveByIdResult {
        if (this.collection.size == 0) {
            return AbstractPersonCollection.RemoveByIdResult.NOT_FOUND
        } else if(collection.removeIf {
                    it.id == id
            }) {
            return AbstractPersonCollection.RemoveByIdResult.DELETED
        }
        return AbstractPersonCollection.RemoveByIdResult.EMPTY
    }

    override fun clear(user: User): AbstractPersonCollection.ClearResult {
        return if (this.collection.any { it.user == user.login}) {
            this.collection.removeIf { elem ->
                elem.user == user.login
            }
            AbstractPersonCollection.ClearResult.DELETED
        } else {
            AbstractPersonCollection.ClearResult.NOT_FOUND
        }
    }

    override fun removeFirst(user: User): AbstractPersonCollection.RemoveFirstResult {
        return if (this.collection.size > 0) {
            this.collection.removeFirst()
            AbstractPersonCollection.RemoveFirstResult.DELETED
        } else {
            AbstractPersonCollection.RemoveFirstResult.NOT_FOUND
        }
    }

    override fun removeGreater(id: Long, user: User): AbstractPersonCollection.RemoveGreaterResult {
        if (this.collection.size == 0) {
            return AbstractPersonCollection.RemoveGreaterResult.EMPTY
        } else if(collection.removeIf {
                    it.id!! > id
                }) {
            return AbstractPersonCollection.RemoveGreaterResult.DELETED
        }
        return AbstractPersonCollection.RemoveGreaterResult.GREAT_NOT_FOUND
    }

    override fun maxByCoordinates(user: User): Long {
        val person: Person? = this.collection.maxByOrNull {
            abs(it.coordinates.x) + abs(it.coordinates.y)
        }?.let { return@maxByCoordinates it.coordinates.x }
        return (0).toLong()
    }

//    override fun filterContainsName(name: String): Iterator<Person> {
//        return this.collection.filter { it.name.contains(name) }.iterator()
//    }
//
//    override fun sortDescending(): Iterator<Person> {
//        return this.collection.sortedDescending().iterator()
//    }

    override fun iterator(): Iterator<Person> {
        return this.collection.iterator()
    }

}