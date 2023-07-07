package com.viennth.app.understanding.coroutines

import com.viennth.app.understanding.printLog

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main () {
}

fun runBlockingWithCoroutineScope() = runBlocking {
    val startTime = System.currentTimeMillis()
    printLog("Begin of runBlocking at ${System.currentTimeMillis() - startTime}ms") //1
    // coroutineScope is Asynchronous, lock any function after it.
    coroutineScope {
        delay(2000)
        printLog("My name is Child at ${System.currentTimeMillis() - startTime}ms") // 2
    }
    printLog("End of runBlocking at ${System.currentTimeMillis() - startTime}ms") // 3

    //Result
    // =>> [main] Begin of runBlocking at 0ms
    // =>> [main] My name is Child at 2009ms
    // =>> [main] End of runBlocking at 2010ms
}

fun runBlockingWithLaunch() = runBlocking {
    val startTime = System.currentTimeMillis()
    printLog("Begin of runBlocking at ${System.currentTimeMillis() - startTime}ms") // 1
    launch {
        delay(2000)
        printLog("My name is Child at ${System.currentTimeMillis() - startTime}ms") // 3
    }
    printLog("End of runBlocking at ${System.currentTimeMillis() - startTime}ms") // 2

    // Result
    // =>> [main] Begin of runBlocking at 0ms
    // =>> [main] End of runBlocking at 1ms
    // =>> [main] My name is Child at 2011ms
}

fun nestedCoroutine1() = runBlocking {
    val startTime = System.currentTimeMillis()
    printLog("Begin of runBlocking at ${System.currentTimeMillis() - startTime}ms") //1
    coroutineScope {
        launch {
            delay(2000)
            printLog("My name is Child 2000 at ${System.currentTimeMillis() - startTime}ms") //4
        }
        launch {
            delay(1000)
            printLog("My name is Child 1000 at ${System.currentTimeMillis() - startTime}ms") // 3
        }
        printLog("coroutineScope at ${System.currentTimeMillis() - startTime}ms") // 2
    }

    printLog("End of runBlocking at ${System.currentTimeMillis() - startTime}ms") //5

    // Result
    // =>> [main] Begin of runBlocking at 0ms
    // =>> [main] coroutineScope at 4ms
    // =>> [main] My name is Child 1000 at 1011ms
    // =>> [main] My name is Child 2000 at 2011ms
    // =>> [main] End of runBlocking at 2012ms
}

fun nestedCoroutine2() = runBlocking {
    val startTime = System.currentTimeMillis()
    coroutineScope {
        launch {
            coroutineScope {
                delay(4000)
                printLog("coroutineScope inside launch at ${System.currentTimeMillis() - startTime}ms") // 3
            }
            printLog("My name is Child 2000 at ${System.currentTimeMillis() - startTime}ms") // 4
        }
        launch {
            delay(1000)
            printLog("My name is Child 1000 at ${System.currentTimeMillis() - startTime}ms") // 2
        }
        printLog("coroutineScope at ${System.currentTimeMillis() - startTime}ms") // 1
    }

    printLog("runBlocking at ${System.currentTimeMillis() - startTime}ms") // 5

    // Result
    // =>> [main] coroutineScope at 4ms
    // =>> [main] My name is Child 1000 at 1013ms
    // =>> [main] coroutineScope inside launch at 4011ms
    // =>> [main] My name is Child 2000 at 4012ms
    // =>> [main] runBlocking at 4012ms
}

fun nestedCoroutine3() = runBlocking {
    val startTime = System.currentTimeMillis()
    coroutineScope {
        // coroutineScope block all function after
        coroutineScope {
            delay(3000)
            printLog("My name is Child 3000 at ${System.currentTimeMillis() - startTime}ms") // 1
        }
        // launch & print was blocked by coroutineScope 3000ms
        launch {
            delay(1000)
            printLog("My name is Child 1000 at ${System.currentTimeMillis() - startTime}ms") //3
        }
        printLog("coroutineScope at ${System.currentTimeMillis() - startTime}ms") //2
    }
    printLog("runBlocking at ${System.currentTimeMillis() - startTime}ms") //4

    // Result
    // =>> [main] My name is Child 3000 at 3011ms
    // =>> [main] coroutineScope at 3013ms
    // =>> [main] My name is Child 1000 at 5023ms
    // =>> [main] runBlocking at 5024ms
}

fun nestedCoroutine4() = runBlocking {
    val startTime = System.currentTimeMillis()
    launch {
        // coroutineScope block all function after
        coroutineScope {
            delay(3000)
            printLog("My name is Child 3000 at ${System.currentTimeMillis() - startTime}ms") // 2
        }
        // launch & print was blocked by coroutineScope 3000ms
        launch {
            delay(1000)
            printLog("My name is Child 1000 at ${System.currentTimeMillis() - startTime}ms") //4
        }
        printLog("coroutineScope at ${System.currentTimeMillis() - startTime}ms") //3
    }
    printLog("runBlocking at ${System.currentTimeMillis() - startTime}ms") //1

    // Result
    // =>> [main] runBlocking at 1ms
    // =>> [main] My name is Child 3000 at 3013ms
    // =>> [main] coroutineScope at 3013ms
    // =>> [main] My name is Child 1000 at 5015ms
}

fun nestedCoroutine5() = runBlocking {
    val startTime = System.currentTimeMillis()
    launch {
        // coroutineScope block all function after
        launch {
            delay(3000)
            printLog("My name is Child 3000 at ${System.currentTimeMillis() - startTime}ms") // 4
        }
        // launch & print was blocked by coroutineScope 3000ms
        launch {
            delay(1000)
            printLog("My name is Child 1000 at ${System.currentTimeMillis() - startTime}ms") //3
        }
        printLog("coroutineScope at ${System.currentTimeMillis() - startTime}ms") //2
    }
    printLog("runBlocking at ${System.currentTimeMillis() - startTime}ms") //1

    // Result
    // =>> [main] runBlocking at 1ms
    // =>> [main] coroutineScope at 3ms
    // =>> [main] My name is Child 1000 at 1011ms
    // =>> [main] My name is Child 3000 at 3008ms
}
