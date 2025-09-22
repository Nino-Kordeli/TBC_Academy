package com.example.tbcacademy

fun main() {
    println(usg(12, 24))
    println(usj(125, 5))
    println(usg(114, 12))
    println(usj(468, 22))

    containsSymbol("hii$")
    containsSymbol("Hello")

    println("Sum of even numbers is ${sumOfEvenNums()}")

}

fun usg(firstNum: Int, secondNum: Int): Int {
    var a = firstNum
    var b = secondNum
    var remainder: Int
    if (a == 0 && b == 0) return 0
    while (b != 0) {
        remainder = a % b
        a = b
        b = remainder
    }
    return a
}

fun usj(firstNum: Int, secondNum: Int): Int {
    val a = firstNum
    val b = secondNum
    if (a == 0 || b == 0) return 0
    val usj = (a * b) / usg(a, b)
    return usj
}

fun containsSymbol(word: String) {
    val symbol = '$'
    var found = false
    for (i in word) {
        if (i == symbol) {
            found = true
            break
        }
    }
    if (found) {
        println("The word $word contains the symbol $")
    } else {
        println("The word $word does not contain the symbol $")
    }
}

fun sumOfEvenNums():Int {
    var sum = 0
    for (i in 1..100) {
        if (i % 2 == 0) {
            sum += i
        }
    }
    return sum
}

