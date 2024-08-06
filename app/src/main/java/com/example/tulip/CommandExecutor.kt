package com.example.tulip

import kotlin.system.exitProcess

class CommandExecutor {

    fun execute(input:String):String{
        val decode=input.split(" ")
        val command=decode[0].uppercase()
        when (command) {
            "LS" -> return "README.txt\n@#$%$%.bad\n"
            "EXIT" -> exitProcess(0)

        }

        return "this is an unknown command"
    }
}