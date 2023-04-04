package com.github.altpaka.consoleApp.users
import kotlinx.serialization.Serializable

@Serializable
class User(
    val login: String,
    val password: String
) {
}