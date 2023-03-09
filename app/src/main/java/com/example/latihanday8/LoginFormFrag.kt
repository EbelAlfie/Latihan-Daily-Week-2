package com.example.latihanday8

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class LoginFormFrag: Fragment() {
    private val VALID_LENGTH = 1
    private val LONG_LENGTH = 2
    private val SHORT_LENGTH = 0

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var loginBtn: Button
    private lateinit var msgText: TextView

    lateinit var validUsers: List<User>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initValidUsers()
        initView(view)

        loginBtn.setOnClickListener {
            val userInput = User(etUsername.text.toString(), etPassword.text.toString())

            if (!checkUser(userInput)) return@setOnClickListener
            message(userInput.name, true)
        }
    }

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

    private fun checkUser(userInput: User): Boolean {
        val validUserChar = isValidChar(userInput.name)
        val validPassChar = isValidPassChar(userInput.password)
        val usernameLength = isNameLong(userInput.name) //0 artinya kependekan, 1 artinya sempurna, 2 artinya kepanjangan
        val passwordLength = isPassLong(userInput.password) //sama kyk di atas
        val validUser = isValidUser(userInput)

        return when {
            !validUserChar -> {
                message(getString(R.string.username_char), false); false
            }
            usernameLength == 0 -> {
                message(getString(R.string.username_tooshort), false); false
            }
            usernameLength == 2 -> {
                message(getString(R.string.username_toolong), false); false
            }
            !validPassChar -> {
                message(getString(R.string.pass_char), false); false
            }
            passwordLength == 0 -> {
                message(getString(R.string.pass_tooshort), false); false
            }
            passwordLength == 2 -> {
                message(getString(R.string.pass_toolong), false); false
            }
            !validUser -> {
                message(getString(R.string.invalid_user), false)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun initView(view: View) {
        etUsername = view.findViewById(R.id.et_username)
        etPassword = view.findViewById(R.id.et_password)
        loginBtn = view.findViewById(R.id.btn_submit)
        msgText = view.findViewById(R.id.message)
    }

    private fun initValidUsers() {
        validUsers = listOf(
            User("global1234", "gL0b@l123@"),
            User("loyalty5678", "l0y@lTyS67B"),
            User("indonesia1945", "inD0n355!a19AS"),
            User("alfagift2023", "alF@g1fTz0zE"),
            User("globa12345", "global123.")
        )
    }
    private fun message(msg: String, mode: Boolean) {
        if (!mode) {
            msgText.setTextColor(ContextCompat.getColor(requireActivity(), R.color.red))
            msgText.text = getString(R.string.login_gagal, msg)
        }else {
            msgText.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green))
            msgText.text = getString(R.string.login_berhasil, msg)
        }
    }
}