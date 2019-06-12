package com.avast.kotlinnativeandroidios

expect fun platformName(): String

fun getSharedModuleString(): String {
    return "Shared module on platform '${platformName()}'"
}