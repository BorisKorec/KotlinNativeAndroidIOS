package com.avast.kotlinnativeandroidios

import platform.Foundation.NSLog

actual fun platformName(): String {
    return "iOS"
}

actual fun logMessage(message: String) {
    NSLog(message)
}