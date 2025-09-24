package com.example.tbcacademy

fun main() {
    println("Enter the first sequence")
    val x = readln()
    println("Enter the second sequence")
    val y = readln()
    println("Enter operation type")
    val operation = readln()


    var numbersOnly1 = ""
    var numbersOnly2 = ""
    for (i in x) {
        if (i.isDigit()) {
            numbersOnly1 += i
        }
    }
    println(numbersOnly1)
    for (j in y) {
        if (j.isDigit()) {
            numbersOnly2 += j
        }
    }
    println(numbersOnly2)
}