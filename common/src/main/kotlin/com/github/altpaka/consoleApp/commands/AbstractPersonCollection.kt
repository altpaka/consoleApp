package com.github.altpaka.consoleApp.commands

import com.github.altpaka.consoleApp.model.Person
import com.github.altpaka.consoleApp.users.User
import java.time.LocalDate


interface AbstractPersonCollection : Iterable<Person> {
    /**
     * Вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
     */
    fun info(user: User): Information

    /**
     * Добавить новый элемент в коллекцию
     */
    fun add(person: Person, user: User): Long

    /**
     * Обновить значение элемента коллекции, id которого равен заданному
     */
    fun update(id: Long, person: Person, user: User): UpdateResult

    /**
     * Удалить элемент из коллекции по его id
     */
    fun removeById(id: Long, user: User): RemoveByIdResult

    /**
     * Очистить коллекцию
     */
    fun clear(user: User): ClearResult

    /**
     * Удалить первый элемент из коллекции
     */
    fun removeFirst(user: User): RemoveFirstResult

    /**
     * Удалить из коллекции все элементы, превышающие заданный
     */
    fun removeGreater(id: Long, user: User): RemoveGreaterResult

    /**
     * Вывести любой объект из коллекции, значение поля coordinates которого является максимальным
     */
    fun maxByCoordinates(user: User): Long

//    /**
//     * Вывести элементы, значение поля name которых содержит заданную подстроку
//     */
//    fun filterContainsName(name: String, user: User): Iterator<Person>
//
//    /**
//     * Вывести элементы коллекции в порядке убывания
//     */
//    fun sortDescending(user: User): Iterator<Person>

    class Information(
            val elemCount: Int = 0,
            val initDate: LocalDate
    ) {
        val typeOfCollection = "Население"
    }

    enum class ClearResult(
        val isSuccess: Boolean
    ){
        DELETED(true),
        NOT_FOUND(false)
    }

    enum class UpdateResult(
            val isSuccess: Boolean
    ) {
        EMPTY(false),
        UPDATED(true),
        NOT_FOUND(false)
    }

    enum class RemoveByIdResult(
            val isSuccess: Boolean
    ) {
        EMPTY(false),
        DELETED(true),
        NOT_FOUND(false)
    }

    enum class RemoveFirstResult(
        val isSuccess: Boolean
    ) {
        EMPTY(false),
        DELETED(true),
        NOT_FOUND(false)
    }

    enum class RemoveGreaterResult(
            val isSuccess: Boolean
    ) {
        EMPTY(false),
        DELETED(true),
        GREAT_NOT_FOUND(false)
    }

}