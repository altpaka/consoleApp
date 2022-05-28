package com.github.Polina3116.lab5

import kotlin.text.isBlank
import java.lang.NullPointerException
import java.lang.IllegalArgumentException

class Location(private val x: Double, //Поле не может быть null
               private val y: Float?, //Строка не может быть пустой, Поле может быть null
               private val name: String?) {
    init {
        if (y == null) throw NullPointerException("y can not be null")
        require(!(name != null && name.isBlank())) { "name can not be empty" }
    }
}