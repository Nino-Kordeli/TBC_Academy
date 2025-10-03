package com.example.tbcacademy

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcacademy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val container = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setListeners()
    }

    private fun groupAnagrams(list: List<String>): List<List<String>> {
        if (list.isEmpty()) return emptyList()
        val map = mutableMapOf<String, MutableList<String>>()
        for (word in list) {
            val chars = word.lowercase().toCharArray()
            chars.sort()
            val key = String(chars)

            if (map.containsKey(key)) {
                map[key]?.add(word)
            } else {
                map[key] = mutableListOf(word)
            }
        }
        return map.values.toList()
    }

    private fun setListeners() = with(binding) {
        saveButton.setOnClickListener {
            val text = anagramTextInput.getInputText()
            if (text.isNotEmpty()) {
                if (container.contains(text)) {
                    makeToast("Word already on the list")
                } else {
                    container.add(text)
                    anagramTextInput.text?.clear()
                    makeToast("Saved: \"$text\"")
                }
            } else {
                makeToast("You must enter a word")
            }
        }

        outputButton.setOnClickListener {
            if (container.isEmpty()) {
                anagramsListOutput.text = "no words saved"
                return@setOnClickListener
            }
            val groups = groupAnagrams(container)
            val strBuilder = StringBuilder()
            groups.forEach { groups ->
                strBuilder.append("[${groups.joinToString(", ")}]\n")
            }
            anagramsListOutput.text = strBuilder.toString().trim()
        }

        clearButton.setOnClickListener {
            container.clear()
            anagramsListOutput.text = ""
            anagramTextInput.text?.clear()
        }
    }

    private fun makeToast(text: String) {
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
    }

    private fun EditText.getInputText() = this.text.toString().trim()
}