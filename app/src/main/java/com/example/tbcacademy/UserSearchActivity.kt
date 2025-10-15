package com.example.tbcacademy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.tbcacademy.databinding.ActivityUserSearchBinding
import com.example.tbcacademy.utils.ui.hide
import com.example.tbcacademy.utils.ui.show
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserSearchBinding

    private val users = mutableListOf(
        User(1, "გრიშა", "ონიანი", 1724647601641, "სტალინის სახლმუზეუმი", "grisha@mail.ru"),
        User(
            2,
            "Jemal",
            "Kakauridze",
            1714647601641,
            "თბილისი, ლილოს მიტოვებული ქარხანა",
            "jemal@gmail.com"
        ),
        User(2, "Omger", "Kakauridze", 1724647701641, "თბილისი, ასათიანი 18", "omger@gmail.com"),
        User(32, "ბორის", "გარუჩავა", 1714947701641, "თბილისი, იაშვილი 14", ""),
        User(1, "აბთო", "სიხარულიძე", 1711947701641, "ფოთი", "tebzi@gmail.com", null)
    )

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val newUser = result.data?.getParcelableExtra("newUser", User::class.java)
            val searchText = result.data?.getStringExtra("searchText")
            if (newUser != null) users.add(newUser)

            if (!searchText.isNullOrEmpty()) {
                binding.etSearchField.setText(searchText)
                val user = searchUser(searchText)
                if (user != null) {
                    binding.tvMessage.text = formatUser(user)
                    binding.btnAddNewUser.hide()
                } else {
                    binding.tvMessage.text = getString(R.string.user_not_found)
                    binding.btnAddNewUser.show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddNewUser.hide()

        binding.etSearchField.addTextChangedListener { editable ->
            val query = editable.toString()
            val user = searchUser(query)
            if (user != null) {
                binding.tvMessage.text = formatUser(user)
                binding.btnAddNewUser.hide()
            } else {
                binding.tvMessage.text = getString(R.string.user_not_found)
                binding.btnAddNewUser.show()
            }
        }

        binding.btnAddNewUser.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            launcher.launch(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun searchUser(query: String): User? {
        return users.find { user ->
            listOf(
                user.firstName,
                user.lastName,
                user.email,
                formatBirthday(user.birthday),
                user.address,
                user.desc
            ).any { it?.contains(query, ignoreCase = true) == true }
        }
    }

    private fun formatBirthday(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        return dateFormat.format(Date(timestamp))
    }

    private fun formatUser(user: User): String {
        return "ID: ${user.id}\n" +
                "Name: ${user.firstName} ${user.lastName}\n" +
                "Birthday: ${formatBirthday(user.birthday)}\n" +
                "Address: ${user.address}\n" +
                "Email: ${user.email}\n" +
                "Desc: ${user.desc ?: "-"}"
    }
}
