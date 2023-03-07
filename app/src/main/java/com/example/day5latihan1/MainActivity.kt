package com.example.day5latihan1

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity: AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var editTextNamaLengkap: EditText
    lateinit var editTextEmail: EditText
    lateinit var radioGroup: RadioGroup
    lateinit var textViewDatePicker: TextView
    lateinit var imageButtonCalendarBtn: ImageButton
    lateinit var editTextPassword: EditText
    lateinit var editTextAlamat : EditText
    lateinit var spinner: Spinner
    lateinit var registerBtn: Button

    private val calendar = Calendar.getInstance()

    private lateinit var userInput: User
    private var user: User? = null

    private var pendidikanTerakhir: String = "SD"
    var index = 0
    var choosenDay = 0
    var choosenMonth = 0
    var choosenYear = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this.intent.getParcelableExtra("Data user", User::class.java)
        } else this.intent.getParcelableExtra("Data user")


        initView()
        initAdapter()

        spinner.onItemSelectedListener = this

        datePickerInit()
        if (user != null) initUser()

        registerBtn.setOnClickListener {
            try {
                if (!gatherUserData()) return@setOnClickListener
                val intent = Intent(this, PageAwal::class.java)
                intent.putExtra("Data User", userInput)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initUser() {
        editTextNamaLengkap.setText(user!!.namaText)
        editTextEmail.setText(user!!.emailText)
        radioGroup.check(if (user!!.gender) R.id.radio_btn_girl else R.id.radio_btn_laki)
        textViewDatePicker.text = user!!.getTglLahir()
        editTextPassword.setText(user!!.passwordText)
        editTextAlamat.setText(user!!.alamatText)
        choosenDay = user!!.tanggalLahir
        choosenMonth = user!!.bulanInt
        choosenYear = user!!.tahunlahir
        //spinner
        spinner.setSelection(user!!.index)
    }

    private fun initView() {
        editTextNamaLengkap = findViewById(R.id.et_nama)
        editTextEmail = findViewById(R.id.et_email)
        radioGroup = findViewById(R.id.rg_gender)
        textViewDatePicker = findViewById(R.id.tv_date_picker)
        imageButtonCalendarBtn = findViewById(R.id.iv_calendar)
        editTextPassword = findViewById(R.id.et_password)
        editTextAlamat = findViewById(R.id.et_alamat)
        spinner = findViewById(R.id.sp_pendidikan)
        registerBtn = findViewById(R.id.btn_submit)

    }

    private fun initAdapter() {
        ArrayAdapter.createFromResource(
            this,
            R.array.list_pendidikan,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun datePickerInit() {
        imageButtonCalendarBtn.setOnClickListener {
            val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                choosenDay = dayOfMonth
                choosenMonth = monthOfYear + 1
                choosenYear = year
                textViewDatePicker.text = getString(R.string.dob, choosenDay, choosenMonth, choosenYear)

            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            dpd.show()
        }
    }

    private fun isValidName(data: Triple<String, String, EditText>): Boolean {
        if (data.second.isBlank()) {
            errorMsg(data.third, getString(R.string.field_kosong, data.first))
            return false
        }
        if (data.second[0].isWhitespace()) {
            errorMsg(data.third, getString(R.string.field_invalid, data.first))
            return false
        }
        when(data.first) {
            getString(R.string.nama) -> {
                if (data.second.length !in 8..20) {
                    errorMsg(data.third, getString(R.string.nama_length_error))
                    return false
                }
            }
            getString(R.string.email) ->{
                if (!data.second.matches(Patterns.EMAIL_ADDRESS.toRegex())) {
                    errorMsg(data.third, getString(R.string.email_format_error))
                    return false
                }
            }
            getString(R.string.password) -> {
                if (data.second.length !in 8..15) {
                    errorMsg(data.third, getString(R.string.password_length_error))
                    return false
                }
            }
        }
        return true
    }

    private fun errorMsg(view: EditText, msg: String) {
        view.error = msg
        view.requestFocus()
    }

    private fun gatherUserData(): Boolean {
        val namaUser = editTextNamaLengkap.text.toString()
        val passwordUser = editTextPassword.text.toString()
        val emailUser = editTextEmail.text.toString()
        val alamatUser = editTextAlamat.text.toString()

        if (!isValidName(Triple(getString(R.string.nama) , namaUser, editTextNamaLengkap)) || !isValidName(Triple(getString(R.string.email) , emailUser, editTextEmail))
            || !isValidName(Triple(getString(R.string.password) , passwordUser, editTextPassword)) || !isValidName(Triple(getString(R.string.alamat) , alamatUser, editTextAlamat))) return false

        if (choosenDay == 0 || choosenMonth == 0 || choosenYear == 0) {
            Toast.makeText(this, getString(R.string.tanggal_missing), Toast.LENGTH_LONG).show()
            return false
        }
        userInput = User(namaUser, emailUser, passwordUser, alamatUser,
            radioGroup.checkedRadioButtonId == R.id.radio_btn_girl, pendidikanTerakhir,
            choosenDay, choosenMonth, choosenYear, index)
        return true
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        pendidikanTerakhir = p0?.getItemAtPosition(p2).toString()
        index = p2
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}