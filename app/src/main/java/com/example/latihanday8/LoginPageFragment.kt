package com.example.latihanday8

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.latihanday8.databinding.LoginPageBinding

class LoginPageFragment: Fragment() {
    companion object {
        private const val VALID_LENGTH = 1
        private const val LONG_LENGTH = 2
        private const val SHORT_LENGTH = 0
    }

    private lateinit var binding: LoginPageBinding

    lateinit var validUsers: List<UserModel>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initValidUsers()

        binding.btnSubmit.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.apply {
                setTitle(getString(R.string.submit_title))
                setIcon(activity?.let { it1 ->
                    ResourcesCompat.getDrawable(
                        it1.resources,
                        R.drawable.baseline_warning_24,
                        null
                    )
                })
                setMessage(getString(R.string.submit_confirm_msg))
                setPositiveButton(getString(R.string.ya)) { _, _ ->
                    login()
                }
                setNegativeButton(getString(R.string.tidak)) { dialog, _->
                    dialog.dismiss()
                }
                show()
            }
        }
    }

    private fun login() {
        val userInput = UserModel(binding.etUsername.text.toString(), binding.etPassword.text.toString())
        if (!checkUser(userInput)) return
        message(requireContext(), getString(R.string.greetings, userInput.name), R.drawable.baseline_check_circle_24, getString(R.string.login_berhasil))
    }

    private fun isValidUser(user: UserModel): Boolean {
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

    private fun checkUser(userInput: UserModel): Boolean {
        val validUserChar = isValidChar(userInput.name)
        val validPassChar = isValidPassChar(userInput.password)
        val usernameLength = isNameLong(userInput.name) //0 artinya kependekan, 1 artinya sempurna, 2 artinya kepanjangan
        val passwordLength = isPassLong(userInput.password) //sama kyk di atas
        val validUser = isValidUser(userInput)

        val invalidIcon = R.drawable.baseline_close_24

        return when {
            !validUserChar -> {
                message(requireContext(), getString(R.string.username_char), invalidIcon, getString(R.string.login_gagal)); false
            }
            usernameLength == 0 -> {
                message(requireContext(), getString(R.string.username_tooshort), invalidIcon, getString(R.string.login_gagal)); false
            }
            usernameLength == 2 -> {
                message(requireContext(), getString(R.string.username_toolong), invalidIcon, getString(R.string.login_gagal)); false
            }
            !validPassChar -> {
                message(requireContext(), getString(R.string.pass_char), invalidIcon, getString(R.string.login_gagal)); false
            }
            passwordLength == 0 -> {
                message(requireContext(), getString(R.string.pass_tooshort), invalidIcon, getString(R.string.login_gagal)); false
            }
            passwordLength == 2 -> {
                message(requireContext(), getString(R.string.pass_toolong), invalidIcon, getString(R.string.login_gagal)); false
            }
            !validUser -> {
                message(requireContext(), getString(R.string.invalid_user), invalidIcon, getString(R.string.login_gagal)); false
            }
            else -> {
                true
            }
        }
    }

    private fun initValidUsers() {
        validUsers = listOf(
            UserModel("global1234", "gL0b@l123@"),
            UserModel("loyalty5678", "l0y@lTyS67B"),
            UserModel("indonesia1945", "inD0n355!a19AS"),
            UserModel("alfagift2023", "alF@g1fTz0zE"),
            UserModel("globa12345", "global123.")
        )
    }
    private fun message(context: Context, msg: String, icon: Int, title: String) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.apply{
            setIcon(ResourcesCompat.getDrawable(context.resources, icon,null))
            setTitle(title)
            setMessage(msg)
            setPositiveButton(getString(R.string.ok)) { dialog, _ -> dialog.dismiss() }
            show()
        }
    }
}