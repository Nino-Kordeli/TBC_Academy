package com.example.tbcacademy

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val languageSwitch: SwitchCompat = findViewById(R.id.languageSwitch)
        val titleText: TextView = findViewById(R.id.titleText)
        val button: Button = findViewById(R.id.buttonCalculate)
        val textInput: EditText = findViewById(R.id.textInput)
        val textResult: TextView = findViewById(R.id.resultText)

        languageSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                titleText.text = "Enter a number"
                button.text = "Calculate"
            } else {
                titleText.text = "შეიყვანეთ რიცხვი"
                button.text = "გამოთვლა"
            }
        }

        button.setOnClickListener {
            val userInput = textInput.text.toString().trim()
            val isEnglish = languageSwitch.isChecked

            if (userInput.isEmpty()) {
                textResult.text =
                    if (isEnglish) "Please ender a valid number"
                    else "გთხოვთ შეიყვანოთ ვალიდური რიცხვითი მნიშვნელობა"
            } else if (userInput.all { it.isDigit() }) {
                val number = userInput.toInt()
                if (number in 0..1000) {
                    val words =
                        if (isEnglish) numbersToWordsEnglish(number)
                        else numbersToWordsGeorgian(number)
                    textResult.text = words
                } else {
                    textResult.text =
                        if (isEnglish) "Enter a number from 0 to 1000 "
                        else "შეიყვანეთ რიცხვი 0-დან 1000-მდე"
                }
            } else {
                textResult.text = if (isEnglish) "Please enter only numbers"
                else "გთხოვთ შეიყვანოთ მხოლოდ რიცხვითი მნიშვნელობა"

            }
        }
    }
}

fun numbersToWordsGeorgian(number: Int): String {
    if (number == 0) return "ნული"
    if (number == 1000) return "ათასი"
    val singleDigits =
        arrayOf(
            "",
            "ერთი",
            "ორი",
            "სამი",
            "ოთხი",
            "ხუთი",
            "ექვსი",
            "შვიდი",
            "რვა",
            "ცხრა"
        )
    val tensWithSingles =
        arrayOf(
            "",
            "თერთმეტი",
            "თორმეტი",
            "ცამეტი",
            "თოთხმეტი",
            "თხუთმეტი",
            "თექვსმეტი",
            "ჩვიდმეტი",
            "თვრამეტი",
            "ცხრამეტი"
        )

    val tensOnly =
        arrayOf(
            "",
            "ათი",
            "ოცი",
            "ოცდაათი",
            "ორმოცი",
            "ორმოცდაათი",
            "სამოცი",
            "სამოცდაათი",
            "ოთხმოცი",
            "ოთხმოცდაათი",
        )

    val hundreds =
        arrayOf(
            "",
            "ასი",
            "ორასი",
            "სამასი",
            "ოთხასი",
            "ხუთასი",
            "ექვსასი",
            "შვიდასი",
            "რვაასი",
            "ცხრაასი"
        )

    val hundredDigit = number / 100
    val lastTwoDigits = number % 100
    val tenDigits = lastTwoDigits / 10
    val singleDigit = number % 10

    val strBuilder = StringBuilder()

    if (hundredDigit > 0) {
        var hundredWord = hundreds[hundredDigit]
        if (lastTwoDigits > 0) hundredWord = hundredWord.dropLast(1)
        strBuilder.append(hundredWord)
        strBuilder.append(" ")
    }

    if (lastTwoDigits in 11..19) {
        strBuilder.append(tensWithSingles[lastTwoDigits - 10])
        strBuilder.append(" ")
    } else {
        if (tenDigits > 0) {
            var tensWord = tensOnly[tenDigits]
            if (singleDigit > 0) {
                tensWord = when {
                    tensWord.endsWith("ათი") -> tensWord.dropLast(3)
                    tensWord.endsWith("ი") -> tensWord.dropLast(1)
                    else -> tensWord
                }
                if (tenDigits % 2 == 0) tensWord += "და"
                strBuilder.append(tensWord)
                strBuilder.append(" ")
                if (tenDigits in arrayOf(3, 5, 7, 9) && singleDigit in 1..9) {
                    strBuilder.append(tensWithSingles[singleDigit])
                    strBuilder.append(" ")
                } else {
                    strBuilder.append(singleDigits[singleDigit])
                    strBuilder.append(" ")
                }
            } else {
                strBuilder.append(tensWord)
            }
        } else {
            if (singleDigit > 0)
                strBuilder.append(singleDigits[singleDigit])
            strBuilder.append(" ")
        }
    }
    return strBuilder.toString()
}

fun numbersToWordsEnglish(number: Int): String {
    if (number == 0) return "Zero"
    if (number == 1000) return "Thousand"

    val singleDigitsEN =
        arrayOf(
            "",
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine"
        )
    val tensWithSinglesEN =
        arrayOf(
            "",
            "eleven",
            "twelve",
            "thirteen",
            "fourteen",
            "fifteen",
            "sixteen",
            "seventeen",
            "eighteen",
            "nineteen"
        )
    val tensEN =
        arrayOf(
            "",
            "ten",
            "twenty",
            "thirty",
            "forty",
            "fifty",
            "sixty",
            "seventy",
            "eighty",
            "ninety"
        )

    val hundredDigitEN = number / 100
    val lastTwoDigitsEN = number % 100
    val tenDigitsEN = (number % 100) / 10
    val singleDigitEN = number % 10

    val strBuilderEN = StringBuilder()

    if (hundredDigitEN > 0) {
        strBuilderEN.append(singleDigitsEN[hundredDigitEN])
        strBuilderEN.append(" hundred")
        if (lastTwoDigitsEN > 0) strBuilderEN.append(" ")
    }

    if (lastTwoDigitsEN in 10..19) {
        strBuilderEN.append(tensWithSinglesEN[lastTwoDigitsEN - 10])
    } else {
        if (tenDigitsEN > 1) {
            strBuilderEN.append(tensEN[tenDigitsEN])
            if (singleDigitEN > 0) strBuilderEN.append(" ")
        }
        if (singleDigitEN > 0) {
            strBuilderEN.append(singleDigitsEN[singleDigitEN])
        }
    }
    return strBuilderEN.toString()
}
