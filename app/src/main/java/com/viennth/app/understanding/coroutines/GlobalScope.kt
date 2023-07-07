package com.viennth.app.understanding.coroutines

import com.viennth.app.understanding.printLog
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    cancelWithGlobalScopeAsChild()
}

fun cancelParentCoroutine() = runBlocking {
    val request = launch {
        launch {
            delay(100)
            printLog("job2: I am a child of the request coroutine")   // line code 1
            delay(1000)
            printLog("job2: I will not execute this line if my parent request is cancelled") // line code 2
        }
    }
    delay(500)
    request.cancel() // cancel processing of the request
    delay(1000)
    printLog("main: Who has survived request cancellation?") // line code 3
}

@OptIn(DelicateCoroutinesApi::class)
fun cancelWithGlobalScopeAsChild() = runBlocking {
    val request = launch {
        // it spawns two other jobs, one with GlobalScope
        GlobalScope.launch {
            printLog("job1: GlobalScope and execute independently!")
            delay(1000)
            printLog("job1: I am not affected by cancellation")  // line code 1 này vẫn được in ra mặc dù bị delay 1000ms
        }
        // and the other inherits the parent context
        launch {
            delay(100)
            printLog("job2: I am a child of the request coroutine")
            delay(1000)
            printLog("job2: I will not execute this line if my parent request is cancelled")
        }
    }
    delay(500)
    request.cancel() // cancel processing of the request
    delay(1000) // delay a second to see what happens
    printLog("main: Who has survived request cancellation?")
}
