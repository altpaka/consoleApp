package com.github.altpaka.consoleApp.model

import java.io.Serializable
import java.lang.NullPointerException

class Location(val x: Float?, //Поле не может быть null
               val y: Double?, //Поле не может быть null
               val name: String?): Serializable { //Строка не может быть пустой, Поле может быть null
    init {
        if (y == null || x == null) throw NullPointerException("x or y can not be null")
        require(!(name != null && name.isBlank())) { "name can not be empty" }
    }
}