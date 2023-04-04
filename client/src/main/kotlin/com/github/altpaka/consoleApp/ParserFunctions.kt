package com.github.altpaka.consoleApp

import com.github.altpaka.consoleApp.exception.ParseException
import com.github.altpaka.consoleApp.exception.TriesLimitException
import com.github.altpaka.consoleApp.exception.UnexpectedCommandException
import com.github.altpaka.consoleApp.io.Logger
import com.github.altpaka.consoleApp.model.Color
import com.github.altpaka.consoleApp.model.Coordinates
import com.github.altpaka.consoleApp.model.Location
import com.github.altpaka.consoleApp.model.Person
import java.time.LocalDate
import kotlin.jvm.Throws

/**
 * Verification the validity of an argument
 */
fun <T: Any> tryGet(field: String, t: Int, message: String, number: String.() -> T?) : T? {
    for(i in 0 until t) {
        var data = field
        if(i != 0){
            data = readln()
        }
        data.number().let{ number ->
            if(number == null){
                println("Данные некорректны")
                println(message)
                if (i != t-1) {
                    println("Количество оставшихся попыток: ${t-i-1}")
                }
            }
            else return@tryGet number
        }
    }
    return null
}

/**
 * Verification the validity of an argument execution сonstructor for creating an instance based on file
 */
fun instanceCreate(name: String, xCoordinates: String, yCoordinates: String, height: String, weight: String,
                   passportID: String, hairColor: String, xLocation: String, yLocation: String, nameLocation: String,
                   t: Int) : Person? {

    // ?. - функция или поле берётся, если слева НЕ null, в противном случае результат выражения null
    // ?: - функция или поле берётся, если слева null, в противном случае берётся левая часть и кастуется к NotNull


    // isBlank() - строка, состоящая из пробельных символов
    val name = tryGet(name, t, "Имя не может быть пустой строкой") { takeIf { isNotBlank() } } ?: return@instanceCreate null


    val xCoordinates: Long = tryGet(xCoordinates, t, "Координата Х должна быть целым числом") { toLongOrNull() } ?: return@instanceCreate null


    val yCoordinates: Double = tryGet(yCoordinates, t, "Координата Y должна быть десятичной дробью") { toDoubleOrNull() } ?: return@instanceCreate null


    val height: Long = tryGet(height, t, "Рост человека должен быть целым числом больше нуля") {
        toLongOrNull()?.takeIf { number -> number > 0 }
    } ?: return@instanceCreate null


    val weight: Double = tryGet(weight, t, "Вес человека должен быть дробным числом больше нуля") {
        toDoubleOrNull()?.takeIf { number -> number > 0 }
    } ?: return@instanceCreate null


    val hairColor: Color? = tryGet(hairColor, t, "В качестве типа внешности человека введите один из цветов волос ${Color.getTypes().values}") {
        if ( Color.stringToType.containsKey(this) ) {
            Color.stringToType[this]
        } else {
            null
        }
    }


    val xLocation: Float = tryGet(xLocation, t, "Координата Х должна быть десятичной дробью") { toFloatOrNull() } ?: return@instanceCreate null


    val yLocation: Double = tryGet(yLocation, t, "Координата Y должна быть десятичной дробью") { toDoubleOrNull() } ?: return@instanceCreate null


    val nameLocation: String? = tryGet(nameLocation, t, "Координата Y должна быть десятичной дробью") { takeIf { isNotBlank() }  }

    val location: Location? = try {
        Location(xLocation, yLocation, nameLocation)
    } catch (e: NullPointerException){
        null
    }


    val passportID: String? = tryGet(passportID, t, "ID должно быть любой строкой, не более 41") {
        takeIf { isNotBlank() }  }

    val time: LocalDate = LocalDate.now()

    return Person(
        null,
        name,
        Coordinates(xCoordinates, yCoordinates),
        time,
        height,
        weight,
        passportID,
        hairColor,
        location
    )
}

/**
 * Verification the validity of an argument execution constructor for creating an instance based on console
 */
fun instanceCreate(attempts: Int, logger: Logger) : Person? {
    logger.print("Введите цвет волос человека из предложенных:")
    logger.print(Color.getTypes().toString())
    val hairColor: Color = tryGet(readln(), attempts, "Введите число от 0 до 2") {
        toIntOrNull()?.let(Color::getColor)
    } ?: return@instanceCreate null

    logger.print("Введите имя человека")
    // isBlank() - строка, состоящая из пробельных символов
    val name = tryGet(readln(), attempts, "Имя не может быть пустой строкой") {
        takeIf { isNotBlank() } } ?: return@instanceCreate null

    logger.print("Введите координаты")
    logger.print("Введите координату X в виде целого числа:")
    val xCoordinates: Long = tryGet(readln(), attempts, "Введите целое число")
    { toLongOrNull() } ?: return@instanceCreate null

    logger.print("Введите координату Y в виде десятичной дроби:")
    val yCoordinates: Double = tryGet(readln(), attempts, "Введите целое число") { toDoubleOrNull() } ?: return@instanceCreate null

    val time: LocalDate = LocalDate.now()

    logger.print("Введите рост человека в виде целого числа:")
    val height: Long = tryGet(readln(), attempts, "Рост человека должен быть целым числом больше нуля") {
        toLongOrNull()?.takeIf { number -> number > 0 }
    } ?: return@instanceCreate null

    logger.print("Введите вес человека в виде десятичной дроби:")
    val weight: Double = tryGet(readln(), attempts, "Вес человека должен быть дробным числом больше нуля") {
        toDoubleOrNull()?.takeIf { number -> number > 0 }
    } ?: return@instanceCreate null

    logger.print("Введите id паспорта человека:")
    val passportID: String = tryGet(readln(), attempts, "Введите целое число не больше 41") {
        takeIf { isNotBlank() } } ?: return@instanceCreate null

    logger.print("Введите координаты и название локации")
    logger.print("Введите координату X локации в виде дробного числа:")
    val xLocation: Float = tryGet(readln(), attempts, "Координата Х должна быть десятичной дробью") { toFloatOrNull() } ?: return@instanceCreate null

    logger.print("Введите координату Y локации в виде дробного числа:")
    val yLocation: Double = tryGet(readln(), attempts, "Координата Y должна быть десятичной дробью") { toDoubleOrNull() } ?: return@instanceCreate null

    logger.print("Введите название локации:")
    val nameLocation: String? = tryGet(readln(), attempts, "Название локации должно быть строкой") { takeIf { isNotBlank() }  }

    val location: Location? = try {
        Location(xLocation, yLocation, nameLocation)
    } catch (e: NullPointerException){
        null
    }


    logger.print("Элемент успешно добавлен или обновлён")
    return Person(
        null,
        name,
        Coordinates(xCoordinates, yCoordinates),
        time,
        height,
        weight,
        passportID,
        hairColor,
        location
    )

}

// login could be null? (String? or String)
fun requestLogin(attempts: Int, logger: Logger): String {
    logger.print("Введите логин: ")
    val login = tryGet(
        readln(),
        attempts,
        "Логин не может быть пустой строкой, осталось попыток ввода: $attempts"
    ) {
        takeIf { isNotBlank() }
    } ?: throw TriesLimitException("Вы израсходовали все попытки")
    return login
}

fun requestPassword(attempts: Int, logger: Logger): String {
    logger.print("Введите пароль: ")
    val password = tryGet(
        readln(),
        attempts,
        "Пароль не может быть пустой строкой, осталось попыток ввода: $attempts"
    ) {
        takeIf { isNotBlank() }
    } ?: throw TriesLimitException("Вы израсходовали все попытки")
    return password
}