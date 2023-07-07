package com.viennth.app.understanding.coroutines

import com.viennth.app.understanding.printLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {
    firstResultIsInputSecond1()
}
// For example:
// We launch a coroutine, the first result is input of second
fun firstResultIsInputSecond() = runBlocking {
    launch {
        val value = coroutineScope {
            // Heavy work
            returnVal()
        }
        launch {
            val result = value * value
            printLog("Result: $result")
        }
        printLog("Task launch")
    }
}

// For example:
// We launch a coroutine, the first result is input of second
fun firstResultIsInputSecond1() = runBlocking {

    launch {
        val value =
            withContext(Dispatchers.Default) { returnVal() }
        launch {
            val result = value * value
            printLog("Result: $result") // 100
        }
        printLog("Task launch")
    }
}

suspend fun returnVal(): Int  {
    delay(3000)
    return 10
}
