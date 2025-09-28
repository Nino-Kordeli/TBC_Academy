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

    val firstDigit = number / 100 //345 --- 3
    val lastTwoDigits = number % 100 //345 --- 45
    val secondDigit = lastTwoDigits / 10 //345 --- 4
    val lastDigit = number % 10 //345 --- 5

    val strBuilder = StringBuilder()

    if (firstDigit > 0) { //ესეიგი სამ ციფრიანია
        var hundredWord = hundreds[firstDigit]
        if (lastTwoDigits > 0) hundredWord = hundredWord.dropLast(1)//ასეულს ვაჭრით ბოლო ასოს
        strBuilder.append(hundredWord)
        strBuilder.append(" ")//ვუმატებთ ბოლოში გამოტოვებას
    }

    if (lastTwoDigits in 11..19) {
        strBuilder.append(tensWithSingles[lastTwoDigits - 10])
        strBuilder.append(" ")
    } else {
        if (secondDigit > 0) {
            var tensWord = tensOnly[secondDigit]//ვიღებთ ათეულებს
            if (lastDigit > 0) {//თუ არ ბოლოვდება ნულით
                tensWord = when {
                    tensWord.endsWith("ათი") -> tensWord.dropLast(3)//ვაკლებთ "ათი" - ს
                    tensWord.endsWith("ი") -> tensWord.dropLast(1)//ვაკლებთ "ი" - ს
                    else -> tensWord
                }
                if (secondDigit % 2 == 0) tensWord += "და" //
                strBuilder.append(tensWord)
                strBuilder.append(" ")
                if (secondDigit in arrayOf(3, 5, 7, 9) && lastDigit in 1..9) {
                    strBuilder.append(tensWithSingles[lastDigit])
                    strBuilder.append(" ")
                } else {
                    strBuilder.append(singleDigits[lastDigit])//არამხოლოდ ათეულები --- 47
                    strBuilder.append(" ")
                }
            } else {
                strBuilder.append(tensWord)//მხოლოდ ათეულები
            }
        } else {
            if (lastDigit > 0)
                strBuilder.append(singleDigits[lastDigit])//მხოლოდ ერთეულები
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

    val firstDigitEN = number / 100 // 345 --- 3
    val lastTwoDigitsEN = number % 100 //345 --- 45
    val secondDigitEN = (number % 100) / 10 // 345 --- 4
    val lastDigitEN = number % 10 // 345 --- 5

    val strBuilderEN = StringBuilder()

    if (firstDigitEN > 0) {
        strBuilderEN.append(singleDigitsEN[firstDigitEN])//ვამატებთ ერთეულ სიტყვას "one","two"..
        strBuilderEN.append(" hundred")
        if (lastTwoDigitsEN > 0) strBuilderEN.append(" ")//ვამატებთ გამოტოვებას
    }

    if (lastTwoDigitsEN in 10..19) {
        strBuilderEN.append(tensWithSinglesEN[lastTwoDigitsEN - 10])//ვამატებთ 11-19 რიცხვებს
    } else {//მხოლოდ 20-99
        if (secondDigitEN > 1) {//მხოლოდ ორი და მეტი ათეული
            strBuilderEN.append(tensEN[secondDigitEN])
            if (lastDigitEN > 0) strBuilderEN.append(" ")//თუ არ ბოლოვდება ნულით ვყოფთ ადგილს
        }
        if (lastDigitEN > 0) {
            strBuilderEN.append(singleDigitsEN[lastDigitEN])
        }
    }
    return strBuilderEN.toString()
}
