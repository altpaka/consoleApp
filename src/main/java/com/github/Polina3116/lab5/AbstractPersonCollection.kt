package com.github.Polina3116.lab5

import kotlinx.datetime.Instant


interface AbstractPersonCollection: Iterable<Person>{
    /**
     * info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
     */
    fun info(): Information
    /**
     * add : добавить новый элемент в коллекцию
     */
    fun add(person: Person)
    /**
     * update : обновить значение элемента коллекции, id которого равен заданному
     */
    fun update(id: Int, person: Person): UpdateResult
    /**
     * removeById : удалить элемент из коллекции по его id
     */
    fun removeById(id: Int): RemoveByIdResult
    /**
     * clear : очистить коллекцию
     */
    fun clear()
    /**
     * removeFirst : удалить первый элемент из коллекции
     */
    fun removeFirst(): Boolean
    /**
     * removeGreater : удалить из коллекции все элементы, превышающие заданный
     */
    fun removeGreater(id: Int): RemoveGreaterResult
    /**
     * maxByCoordinates : вывести любой объект из коллекции, значение поля coordinates которого является максимальным
     */
    fun maxByCoordinates(): Person?
    /**
     * filterContainsName : вывести элементы, значение поля name которых содержит заданную подстроку
     */
    fun filterContainsName(name: String): Iterator<Person>
    /**
     * sortDescending : вывести элементы коллекции в порядке убывания
     */
    fun sortDescending(): Iterator<Person>
    class Information(
            val elemCount: Int = 0,
            val initDate: Instant
    ) {
        val typeOfCollection = "Население"
    }
    enum class UpdateResult(
            val isSuccess: Boolean
    ){
        EMPTY(false),
        UPDATED(true),
        NOT_FOUND(false)
    }
    enum class RemoveByIdResult(
            val isSuccess: Boolean
    ){
        EMPTY(false),
        DELETED(true),
        NOT_FOUND(false)
    }
    enum class RemoveGreaterResult(
            val isSuccess: Boolean
    ){
        EMPTY(false),
        DELETED(true),
        NOT_FOUND(false)
    }

}