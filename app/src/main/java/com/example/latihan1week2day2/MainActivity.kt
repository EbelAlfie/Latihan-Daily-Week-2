package com.example.latihan1week2day2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    val UNTUK_HALAMANUTAMA = "Data User"
    private val VALID_LENGTH = 1
    private val LONG_LENGTH = 2
    private val SHORT_LENGTH = 0

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var loginBtn: Button
    private lateinit var msgText: TextView

    lateinit var validUsers: List<User>

    private fun isValidUser(user: User): Boolean {
        val valid = validUsers.filter {validUserName ->
            user.name.equals(validUserName.name, ignoreCase = true) && user.password == validUserName.password
        }
        return valid.isNotEmpty()
    }

    //Check string length
    private fun isNameLong(username: String): Int {
        return when {
            username.length < 6 -> SHORT_LENGTH
            username.length > 15 -> LONG_LENGTH
            else -> VALID_LENGTH
        }
    }

    private fun isPassLong(password: String): Int {
        return when {
            password.length < 8 -> SHORT_LENGTH
            password.length > 20 -> LONG_LENGTH
            else -> VALID_LENGTH
        }
    }

    private fun isValidChar(username: String): Boolean {
        return !username.contains("[^A-Za-z0-9]".toRegex()) && username.contains("[A-Za-z]".toRegex()) && username.contains(
            "[0-9]".toRegex()
        )
    }

    private fun isValidPassChar(password: String): Boolean {
        return !password.contains("[^A-Za-z0-9!@#$%^&*.\\-_]".toRegex()) && password.contains("[A-Za-z]".toRegex()) && password.contains("[0-9]".toRegex()) && password.contains("[!@#$%^&*.\\-_]".toRegex())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initValidUsers()
        initView()

        loginBtn.setOnClickListener {
            val userInput = User(etUsername.text.toString(), etPassword.text.toString())

            if (!checkUser(userInput)) return@setOnClickListener

            val intent = Intent(this, HalamanUtama::class.java)
            intent.putExtra(UNTUK_HALAMANUTAMA, userInput)
            startActivity(intent)
            finish()
        }
    }

    private fun checkUser(userInput: User): Boolean {
        val validUserChar = isValidChar(userInput.name)
        val validPassChar = isValidPassChar(userInput.password)
        val usernameLength = isNameLong(userInput.name) //0 artinya kependekan, 1 artinya sempurna, 2 artinya kepanjangan
        val passwordLength = isPassLong(userInput.password) //sama kyk di atas
        val validUser = isValidUser(userInput)

        return when {
            !validUserChar -> {
                message(getString(R.string.username_char)); false
            }
            usernameLength == 0 -> {
                message(getString(R.string.username_tooshort)); false
            }
            usernameLength == 2 -> {
                message(getString(R.string.username_toolong)); false
            }
            !validPassChar -> {
                message(getString(R.string.pass_char)); false
            }
            passwordLength == 0 -> {
                message(getString(R.string.pass_tooshort)); false
            }
            passwordLength == 2 -> {
                message(getString(R.string.pass_toolong)); false
            }
            !validUser -> {
                message(getString(R.string.invalid_user))
                false
            }
            else -> {
                true
            }
        }
    }

    private fun initView() {
        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        loginBtn = findViewById(R.id.btn_submit)
        msgText = findViewById(R.id.message)
    }

    private fun initValidUsers() {
        validUsers = listOf(
            User("global1234", "gL0b@l123@"),
            User("loyalty5678", "l0y@lTyS67B"),
            User("indonesia1945", "inD0n355!a19AS"),
            User("alfagift2023", "alF@g1fTz0zE")
        )
    }
    private fun message(msg: String) {
        msgText.setTextColor(getColor(R.color.red))
        msgText.text = getString(R.string.login_gagal, msg)
    }
}