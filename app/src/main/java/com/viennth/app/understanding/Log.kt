package com.viennth.app.understanding

fun printLog(message: String) {
    println("=>> [${Thread.currentThread().name}] $message")
}
