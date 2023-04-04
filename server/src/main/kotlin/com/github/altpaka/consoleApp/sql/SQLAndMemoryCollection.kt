package com.github.altpaka.consoleApp.sql

import com.github.altpaka.consoleApp.collection.CollectionInMemory
import com.github.altpaka.consoleApp.commands.AbstractPersonCollection
import com.github.altpaka.consoleApp.exception.CollectionException
import com.github.altpaka.consoleApp.model.Person
import com.github.altpaka.consoleApp.users.User

class SQLAndMemoryCollection: AbstractPersonCollection {
    private val collectionInMemory = CollectionInMemory()
    private val databaseManager = DatabaseManager!!.instance

    fun getCollectionInMemory(): CollectionInMemory {
        return this.collectionInMemory
    }

    fun getSqlCollection(): DatabaseManager {
        return this.databaseManager!!
    }

    @Synchronized
    override fun add(person: Person, user: User): Long {
        val id = this.databaseManager!!.add(person, user)
        person.id = id
        this.collectionInMemory.add(person, user)
        return id
    }

//    @Synchronized
//    override fun addIfMin(name: String, person: Person, user: User): Pair<AbstractPersonCollection.AddIfMinResult, Int?> {
//        val sqlResult = this.databaseManager.addIfMin(name, person, user)
//        val sqlSuccess = sqlResult.first.isSuccess
//        person.id = sqlResult.second
//        val memoryResult = this.collectionInMemory.addIfMin(name, person, user)
//        val memorySuccess = memoryResult.first.isSuccess
//        if (sqlSuccess == memorySuccess) {
//            return memoryResult
//        } else {
//            throw CollectionException("Ошибка добавления минимального элемента:\nsql-результат: $sqlResult\n" +
//                    "результат работы с коллекцией в памяти: $memoryResult")
//        }
//    }

    override fun clear(user: User): AbstractPersonCollection.ClearResult {
        val sqlResult = this.databaseManager!!.clear(user)
        val sqlSuccess = sqlResult.isSuccess
        val memoryResult = this.collectionInMemory.clear(user)
        val memorySuccess = memoryResult.isSuccess
        if (sqlSuccess == memorySuccess) {
            return memoryResult
        } else {
            throw CollectionException("Ошибка удаления принадлежащих Вам элементов:\nsql-результат: $sqlResult\n" +
                    "результат работы с коллекцией в памяти: $memoryResult")
        }
    }


    override fun info(user: User): AbstractPersonCollection.Information {
        return this.databaseManager!!.info(user)
    }

    override fun removeById(id: Long, user: User): AbstractPersonCollection.RemoveByIdResult {
        val sqlResult = this.databaseManager!!.removeById(id, user)
        val sqlSuccess = sqlResult.isSuccess
        val memoryResult = this.collectionInMemory.removeById(id, user)
        val memorySuccess = memoryResult.isSuccess
        if (sqlSuccess == memorySuccess) {
            return memoryResult
        } else {
            throw CollectionException("Ошибка удаления элемента с id $id, который должен принадлежать вам:" +
                    "\nsql-результат: $sqlResult\nрезультат работы с коллекцией в памяти: $memoryResult")
        }
    }

    override fun removeFirst(user: User): AbstractPersonCollection.RemoveFirstResult {
        val result = this.databaseManager!!.removeFirst(user)
        if (result == this.collectionInMemory.removeFirst(user)) {
            return result
        } else {
            throw CollectionException("Ошибка удаления первого элемента, который при этом должен принадлежать вам")
        }
    }
    override fun removeGreater(id: Long, user: User): AbstractPersonCollection.RemoveGreaterResult {
        val sqlResult = this.databaseManager!!.removeGreater(id, user)
        val sqlSuccess = sqlResult.isSuccess
        val memoryResult = this.collectionInMemory.removeGreater(id, user)
        val memorySuccess = memoryResult.isSuccess
        if (sqlSuccess == memorySuccess) {
            return memoryResult
        } else {
            throw CollectionException("Ошибка удаления больших элементов, принадлежащих вам:\nsql-результат:" +
                    " $sqlResult\nрезультат работы с коллекцией в памяти: $memoryResult")
        }
    }

    override fun iterator(): Iterator<Person> {
        return this.collectionInMemory.iterator()
    }


    @Synchronized
    override fun update(id: Long, person: Person, user: User): AbstractPersonCollection.UpdateResult {
        val sqlResult = this.databaseManager!!.update(id, person, user)
        val sqlSuccess = sqlResult.isSuccess
        person.id = id
        val memoryResult = this.collectionInMemory.update(id, person, user)
        val memorySuccess = memoryResult.isSuccess
        if (sqlSuccess == memorySuccess) {
            return memoryResult
        } else {
            throw CollectionException("Ошибка обновления элемента с id $id, который должен принадлежать Вам:" +
                    "\nsql-результат: $sqlResult\nрезультат работы с коллекцией в памяти: $memoryResult")
        }
    }

    override fun maxByCoordinates(user: User): Long {
        return databaseManager!!.maxByCoordinates(user)
    }
}