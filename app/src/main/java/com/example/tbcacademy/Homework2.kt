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

    val num1 = numbersOnly1.toInt()
    val num2 = numbersOnly2.toInt()

    var result = 0
    when (operation) {
        "+" -> result = num1 + num2
        "/" -> result = num1 / num2
        "*" -> result = num1 * num2
        "%" -> result = num1 % num2
        "!" -> result = factorial(num1 / num2)

    }
}

fun factorial(n: Int): Int {
    var factor = 1
    for (i in 1..n) {
        factor *= i
    }
    return factor
}