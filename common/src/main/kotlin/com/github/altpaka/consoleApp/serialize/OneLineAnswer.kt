package com.github.altpaka.consoleApp.serialize

import com.github.altpaka.consoleApp.users.User
import java.io.Serializable

class OneLineAnswer(
    val user: User? = null,
    val result: String
): Serializable