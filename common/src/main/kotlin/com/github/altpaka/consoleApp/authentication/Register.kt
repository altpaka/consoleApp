package com.github.altpaka.consoleApp.authentication

import com.github.altpaka.consoleApp.commands.AbstractDescription
import com.github.altpaka.consoleApp.commands.AuthentificationPersonCollection
import com.github.altpaka.consoleApp.io.BufferLogger
import com.github.altpaka.consoleApp.users.User
import java.io.Serializable
import java.sql.SQLException

class Register(
    private val login: String,
    private val password: String
): com.github.altpaka.consoleApp.authentication.AuthenticationCommand, Serializable {
    override fun execute(logger: BufferLogger, userCollection: AuthentificationPersonCollection) {
        try {
            userCollection.registration(this.login, this.password)
            logger.print("Пользователь успешно зарегистрирован")
        } catch (ex: SQLException) {
            logger.print("Такой пользователь уже есть в базе")
        }
    }

    companion object Description: AbstractDescription {
        override val title: String = "registry"
        override val help: String = "зарегистрироваться"
    }
}