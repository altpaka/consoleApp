package com.github.altpaka.consoleApp.authentication

import com.github.altpaka.consoleApp.commands.AbstractDescription
import com.github.altpaka.consoleApp.commands.AbstractPersonCollection
import com.github.altpaka.consoleApp.commands.AuthentificationPersonCollection
import com.github.altpaka.consoleApp.exception.NotAuthorizedException
import com.github.altpaka.consoleApp.io.BufferLogger
import com.github.altpaka.consoleApp.users.User
import java.io.Serializable

class LogIn(
    private val login: String,
    private val password: String
): AuthenticationCommand, Serializable {
    override fun execute(logger: BufferLogger, userCollection: AuthentificationPersonCollection) {
        try {
            logger.setUser(userCollection.authorization(this.login, this.password))
            logger.print("Пользователь $login успешно авторизован")
            logger.print("Информация о командах доступна по команде 'help'")
        } catch (ex: NotAuthorizedException) {
            logger.print(ex.message)
        }
    }

    companion object Description: AbstractDescription {
        override val title: String = "log_in"
        override val help: String = "авторизоваться"
    }
}