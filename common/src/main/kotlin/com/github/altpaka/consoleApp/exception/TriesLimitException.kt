package com.github.altpaka.consoleApp.exception

import java.lang.Exception

class TriesLimitException(
    override val message: String
): Exception()