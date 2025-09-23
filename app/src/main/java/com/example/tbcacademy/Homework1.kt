package com.example.tbcacademy

fun main() {
    println("-USG & USJ-")
    println(usg(12, 24))
    println(usg(114, 12))
    println(usj(125, 5))
    println("${usj(468, 22)}\n")


    println("-Symbol Checker-")
    println(containsDollarSymbol("hii$"))
    println("${containsDollarSymbol("Hello")}\n")

    println("-Sum of Even Numbers-")
    println("${sumEvenNums(100)}\n")

    println("-Number Reverser-")
    println(reverseNumber(1200))
    println("${reverseNumber(7584900)}\n")


    println("-Palindrome Checker-")
    println(isPalindrome("Racecar"))
    println(isPalindrome("Cake"))
}

fun usg(firstNum: Int, secondNum: Int): Int {
    var a = firstNum
    var b = secondNum
    if (a == 0 && b == 0) return 0
    while (b != 0) {
        val remainder = a % b
        a = b
        b = remainder
    }
    return a
}

fun usj(firstNum: Int, secondNum: Int): Int {
    if (firstNum == 0 || secondNum == 0) return 0
    val usj = (firstNum * secondNum) / usg(firstNum, secondNum)
    return usj
}

fun containsDollarSymbol(word: String): String {
    val symbol = '$'
    var found = false
    for (i in word) {
        if (i == symbol) {
            found = true
            break
        }
    }
    return if (found) {
        "The word \"$word\" contains the symbol $symbol"
    } else {
        "The word \"$word\" does not contain the symbol $symbol"
    }
}

fun sumEvenNums(num: Int): Int {
    if (num <= 0) return 0
    return if (num % 2 == 0) {
        num + sumEvenNums(num - 2)// roca num luwia vaxtebit orit
    } else {
        sumEvenNums(num - 1)
    }
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