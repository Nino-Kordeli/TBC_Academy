package com.example.tbcacademy

fun main() {
    println("-USG & USJ-")
    println(usg(12, 24))
    println(usg(114, 12))
    println(usj(125, 5))
    println(usj(468, 22))
    println()

    println("-Symbol Checker-")
    containsSymbol("hii$")
    containsSymbol("Hello")
    println()

    println("-Sum of Even Numbers-")
    println("Sum of even numbers is ${sumOfEvenNums()}")
    println()

    println("-Number Reverser-")
    println(reverseNumber(1200))
    println(reverseNumber(7584900))
    println()

    println("-Palindrome Checker-")
    println(isPalindrome("Racecar"))
    println(isPalindrome("Cake"))
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

fun sumOfEvenNums(): Int {
    var sum = 0
    for (i in 1..100) {
        if (i % 2 == 0) {
            sum += i
        }
    }
    return sum
}

fun reverseNumber(number: Int): Int {
    var num = number
    var reversedNum = 0
    var remainderDigit: Int
    while (num != 0) {
        remainderDigit = num % 10
        reversedNum = reversedNum * 10 + remainderDigit
        num /= 10
    }
    return reversedNum
}

fun isPalindrome(word: String): String {
    val lowerCaseWord = word.lowercase()
    val length = lowerCaseWord.length
    for (i in 0 until length / 2) {
        if (lowerCaseWord[i] != lowerCaseWord[length - 1 - i])
            return "\"$word\" is not a palindrome"
    }
    return "\"$word\" is a palindrome"
}