package com.github.altpaka.consoleApp.commands

import com.github.altpaka.consoleApp.model.Person
import com.github.altpaka.consoleApp.users.User

interface AuthentificationPersonCollection: AbstractPersonCollection {
    override fun info(user: User): AbstractPersonCollection.Information

    /**
     * Добавить новый элемент в коллекцию
     */
    override fun add(person: Person, user: User): Long

    /**
     * Обновить значение элемента коллекции, id которого равен заданному
     */
    override fun update(id: Long, person: Person, user: User): AbstractPersonCollection.UpdateResult

    /**
     * Удалить элемент из коллекции по его id
     */
    override fun removeById(id: Long, user: User): AbstractPersonCollection.RemoveByIdResult

    /**
     * Очистить коллекцию
     */
    override fun clear(user: User): AbstractPersonCollection.ClearResult

    /**
     * Удалить первый элемент из коллекции
     */
    override fun removeFirst(user: User): AbstractPersonCollection.RemoveFirstResult

    /**
     * Удалить из коллекции все элементы, превышающие заданный
     */
    override fun removeGreater(id: Long, user: User): AbstractPersonCollection.RemoveGreaterResult

    /**
     * Вывести любой объект из коллекции, значение поля coordinates которого является максимальным
     */
    override fun maxByCoordinates(user: User): Long

    fun registration(login: String, password: String): User

    fun authorization(login: String, password: String): User

}