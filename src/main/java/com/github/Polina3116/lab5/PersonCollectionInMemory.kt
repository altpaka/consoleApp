package com.github.Polina3116.lab5


import java.util.*
import kotlin.math.abs

class PersonCollectionInMemory: AbstractPersonCollection{

    private val collection: LinkedList<Person> = LinkedList()

    override fun info(): AbstractPersonCollection.Information {
        return AbstractPersonCollection.Information(this.collection.size, kotlinx.datetime.Clock.System.now())
    }

    override fun add(person: Person) {
        this.collection.add(person)
    }

    override fun update(id: Int, person: Person): AbstractPersonCollection.UpdateResult {
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

    override fun removeById(id: Int): AbstractPersonCollection.RemoveByIdResult {
        if (this.collection.size == 0) {
            return AbstractPersonCollection.RemoveByIdResult.NOT_FOUND
        } else if(collection.removeIf {
                    it.id == id
            }) {
            return AbstractPersonCollection.RemoveByIdResult.DELETED
        }
        return AbstractPersonCollection.RemoveByIdResult.EMPTY
    }

    override fun clear() {
        this.collection.clear()
    }

    override fun removeFirst(): Boolean {
        return if (this.collection.size > 0) {
            this.collection.removeFirst()
            true
        } else {
            false
        }
    }

    override fun removeGreater(id: Int): AbstractPersonCollection.RemoveGreaterResult {
        if (this.collection.size == 0) {
            return AbstractPersonCollection.RemoveGreaterResult.NOT_FOUND
        } else if(collection.removeIf {
                    it.id > id
                }) {
            return AbstractPersonCollection.RemoveGreaterResult.DELETED
        }
        return AbstractPersonCollection.RemoveGreaterResult.EMPTY
    }

    override fun maxByCoordinates(): Person? {
        return this.collection.maxByOrNull { abs(it.coordinates.x) + abs(it.coordinates.y) }
    }

    override fun filterContainsName(name: String): Iterator<Person> {
        return this.collection.filter { it.name.contains(name) }.iterator()
    }

    override fun sortDescending(): Iterator<Person> {
        return this.collection.sortedDescending().iterator()
    }

    override fun iterator(): Iterator<Person> {
        return this.collection.iterator()
    }

}