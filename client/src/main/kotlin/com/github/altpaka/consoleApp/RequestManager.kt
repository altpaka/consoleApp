package com.github.altpaka.consoleApp

import com.github.altpaka.consoleApp.commands.BoundCommand
import com.github.altpaka.consoleApp.input.Exit
import com.github.altpaka.consoleApp.exception.NotAuthorizedException
import com.github.altpaka.consoleApp.exception.ParseException
import com.github.altpaka.consoleApp.exception.TriesLimitException
import com.github.altpaka.consoleApp.exception.UnexpectedCommandException
import com.github.altpaka.consoleApp.io.AbstractStringReader
import com.github.altpaka.consoleApp.io.Logger
import com.github.altpaka.consoleApp.serialize.OneLineAnswer
import com.github.altpaka.consoleApp.serialize.Request
import com.github.altpaka.consoleApp.users.User
import java.net.ConnectException
import java.net.SocketException

object RequestManager {
    @JvmStatic
    @Throws(UnexpectedCommandException::class, ParseException::class)
            fun manage(logger: Logger, stringReader: AbstractStringReader, client: Client){
                var user: User? = null
                for (line in stringReader) {
                    try {
                        if (line != "registry" && line != "log_in" && user == null) {
                            throw NotAuthorizedException("Перед началом работы необходимо авторизоваться")
                        }
                        val command: BoundCommand = CommandParser.parse(line, stringReader, logger)
                        when (command) {
                            is ExecuteScript -> if (user != null) {command.execute(logger, client)}
                            is Exit -> {logger.print("Клиентское приложение завершило работу")
                                client.sock.close()
                                return
                            }
                        }
                        if (line != "execute_script"){
                            try{
                                var request = Request(command)
                                if (line != "registry" && line != "log_in" && user != null){
                                    request = Request(command, user)
                                }

                                client.send(request)

                                val answer: OneLineAnswer = client.recieve()
                                logger.print(answer.result)
                                if (line == "log_in") {
                                    user = answer.user
                                }

                            } catch (ex: ConnectException) {
                                logger.print("Соединение прервано, перезапустите сервер, затем клиента")
                                return
                            }
                            catch (ex: SocketException) {
                                //logger.print("Соединение прервано, перезапустите сервер, затем клиента, ошибка сокета")
                                print(ex.message)
                                return
                            } catch (ex: TriesLimitException) {
                                logger.print("Вы израсходовали все попытки")
                                return
                            }
                        }
                    } catch (e: UnexpectedCommandException) {
                        logger.print(UnexpectedCommandException.message)
                    } catch (e: ParseException) {
                        logger.print(ParseException.message)
                    } catch (e: NotAuthorizedException) {
                        logger.print(e.message)
                    }
                }
            }
}