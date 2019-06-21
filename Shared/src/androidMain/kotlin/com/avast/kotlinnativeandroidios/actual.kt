package com.avast.kotlinnativeandroidios

actual fun platformName(): String {
    return "Android"
}

actual fun logMessage(message: String) {
    println(message)
}