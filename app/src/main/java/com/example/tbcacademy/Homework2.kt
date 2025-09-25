package com.example.tbcacademy

fun main() {
    var programStart = true

    while (programStart) {
        println("Enter the first sequence: ")
        val x = readln()
        println("Enter the second sequence: ")
        val y = readln()
        println("Enter operation type: ")
        val operation = readln()
        var numbersOnly1 = ""
        var numbersOnly2 = ""
        for (i in x) {
            if (i.isDigit()) {
                numbersOnly1 += i
            }
        }
        println("First number: $numbersOnly1")
        for (j in y) {
            if (j.isDigit()) {
                numbersOnly2 += j
            }
        }
        println("Second number: ${numbersOnly2}")

        val num1 = numbersOnly1.toInt()
        val num2 = numbersOnly2.toInt()

        val result = when (operation) {
            "+" -> "The sum of $num1 and $num2 is ${num1 + num2}"
            "/" -> if (num2 == 0) "Can not divide by zero" else "$num1 divided by $num2 is ${num1 / num2}"
            "*" -> "$num1 multiplied by $num2 is ${num1 * num2}"
            "%" -> "After division of $num1 and $num2 the remainder is ${num1 % num2}"
            "!" -> if (num2 == 0) {
                "Can not divide by zero"
            } else {
                val division = num1 / num2
                "Factorial of $num1 and $num2 division is ${factorial(division)}"
            }

            else -> "Invalid Operator"
        }
        println(result)
        println("Do you want to restart the program <Y/N>?")
        val userInput = readln()
        programStart = userInput.equals("Y", ignoreCase = true)
    }
    println("Program Terminated")
}

fun factorial(n: Int): Int {
    var factor = 1
    for (i in 1..n) {
        factor *= i
    }
    return factor
}