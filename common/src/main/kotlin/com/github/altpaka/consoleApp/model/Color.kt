package com.github.altpaka.consoleApp.model

import java.io.Serializable

private var countOfInstances = 0

enum class Color: Serializable {
    RED, BLACK, BLUE, YELLOW, WHITE;


    var index: Int = countOfInstances++

    companion object{
        @JvmStatic
        private val types: Map<Int, Color> = mapOf(
            RED.index to RED,
            BLACK.index to BLACK,
            BLUE.index to BLUE,
            YELLOW.index to YELLOW,
            WHITE.index to WHITE
        )

        @JvmStatic
        fun getColor(index: Int): Color? {
            return types[index]
        }

        @JvmStatic
        fun getTypes(): Map<Int, Color> {
            return types
        }

        @JvmStatic
        val stringToType: Map<String, Color> = mapOf(
            "RED" to RED,
            "BLACK" to BLACK,
            "BLUE" to BLUE,
            "YELLOW" to YELLOW,
            "WHITE" to WHITE
        )

        @JvmStatic
        val typeToString: Map<Color, String> = mapOf(
            RED to "RED",
            BLACK to "BLACK",
            BLUE to "BLUE",
            YELLOW to "YELLOW",
            WHITE to "WHITE",
        )
    }
}