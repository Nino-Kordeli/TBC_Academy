package com.example.tbcacademy

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button: Button = findViewById(R.id.buttonCalculate)
        val textInput: EditText = findViewById(R.id.textInput)
        val textResult: TextView = findViewById(R.id.resultText)

        button.setOnClickListener {
            val userInput = textInput.text.toString()
            if (userInput.isNotEmpty()) {
                val number = userInput.toInt()
                val words = numbersToWordsGeorgian(number)
                textResult.text = words
            } else {
                textResult.text = "გთხოვთ შეიყვანოთ რიცხვითი მნიშვნელობა"
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
            "ცხრაასი,"

        )


}