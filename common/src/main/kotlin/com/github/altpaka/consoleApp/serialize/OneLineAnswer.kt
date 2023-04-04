package com.github.altpaka.consoleApp.serialize
import com.github.altpaka.consoleApp.users.User
import kotlinx.serialization.Serializable

@Serializable
class OneLineAnswer(
    val user: User? = null,
    val result: String
)