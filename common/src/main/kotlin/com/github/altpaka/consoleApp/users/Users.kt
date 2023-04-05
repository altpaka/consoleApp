package com.github.altpaka.consoleApp.users

import java.io.Serializable

class User(
    val login: String,
    val password: String
): Serializable