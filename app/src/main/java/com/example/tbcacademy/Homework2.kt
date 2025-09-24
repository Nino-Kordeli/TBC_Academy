package com.example.tbcacademy

fun main() {
    println("Enter the first sequence")
    val x = readln()
    println("Enter the second sequence")
    val y = readln()
    println("Enter operation type")
    val operation = readln()

    var numbersOnly = ""
    for (i in x) {
        if (i.isDigit()) {
            numbersOnly += i
        }
    }
}