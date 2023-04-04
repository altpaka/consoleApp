package com.github.altpaka.consoleApp.model

import com.github.altpaka.consoleApp.users.User
import kotlinx.datetime.Instant
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * @property id Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
 * @property name Поле не может быть null, Строка не может быть пустой
 * @property coordinates Поле не может быть null
 * @property creationDate Поле не может быть null, Значение этого поля должно генерироваться автоматически
 * @property height Поле не может быть null, Значение поля должно быть больше 0
 * @property weight Значение поля должно быть больше 0
 * @property passportID Значение этого поля должно быть уникальным, Длина строки не должна быть больше 41, Поле может быть null
 * @property hairColor Поле может быть null
 * @property location Поле может быть null
 */
class Person(
    public var id: Long? = null,
    public val name: String,
    public val coordinates: Coordinates,
    public val creationDate: LocalDate,
    public val height: Long,
    public val weight: Double,
    public val passportID: String?,
    public val hairColor: Color?,
    public val location: Location?,
    var user: String? = null
): Comparable<Person>, Serializable {
    init {
        require(id!! > 0) { "id should be bigger than 0" }
        if (name.isBlank()) throw NullPointerException("name can not be empty")
        require(height > 0) { "height should be bigger than 0" }
        require(weight > 0) { "weight should be bigger than 0" }
        require(!(passportID != null && passportID.length > 41)) { "passportID can not be longer that 41" }
    }

    override fun toString(): String {
        return """
             Номер: ${this.id}
             Имя: ${this.name}
             Координаты: ${this.coordinates}
             Дата создания: ${this.creationDate}
             Рост: ${this.height}
             Вес: ${this.weight}
             ID паспорта: ${this.passportID}
             Цвет волос: ${this.hairColor}
             Местоположение: ${this.location}
             """.trimIndent()
    }

    override fun compareTo(other: Person): Int = this.height.compareTo(other.height)

}