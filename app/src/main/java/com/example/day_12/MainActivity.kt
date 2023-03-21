package com.example.day_12

import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.day_12.databinding.ActivityMainBinding
import com.example.day_12.dialogfragment.DatePickerFragment
import com.example.day_12.dialogfragment.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), TimePickerFragment.SetOnTimeChanged, DatePickerFragment.SetOnDateChanged {
    private lateinit var binding: ActivityMainBinding
    private lateinit var timePickerFragment: TimePickerFragment
    private lateinit var datePickerFragment: DatePickerFragment
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAlarm()
        initSpinner()
        initOnClick()
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerOptions.adapter = adapter
        }
    }

    private fun initAlarm() {
        alarmReceiver = AlarmReceiver()
    }

    private fun initOnClick() {
        binding.btnTimeSet.setOnClickListener {
            timePickerFragment = TimePickerFragment()
            timePickerFragment.timeChangedListener = this
            timePickerFragment.show(supportFragmentManager, TimePickerFragment::class.java.simpleName)
        }

        binding.btnDateSet.setOnClickListener {
            datePickerFragment = DatePickerFragment()
            datePickerFragment.dateChangedListener = this
            datePickerFragment.show(supportFragmentManager, DatePickerFragment::class.java.simpleName)
        }

        binding.btnSetOnce.setOnClickListener {
            setAlarm()
        }
    }

    private fun setAlarm() {
        val time = binding.tvTime.text.toString()
        val date = binding.tvDate.text.toString()
        val desc = binding.etDeskripsi.text.toString()
        val label = binding.etLabel.text.toString()

        when (binding.spinnerOptions.selectedItem) {
            "Sekali" -> alarmReceiver.setOneTimeAlarm(this, time, date, label, desc)
            "Harian" -> alarmReceiver.setCustomTimeAlarm(this, time, date, label, desc, 1440)
            "Mingguan" -> alarmReceiver.setCustomTimeAlarm(this, time, date, label, desc, 7)
            "Bulanan" -> alarmReceiver.setCustomTimeAlarm(this, time, date, label, desc, 30)
        }
    }

    override fun onTimeChanged(hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())

        binding.tvTime.text = formatter.format(calendar.time)
    }

    override fun onDateChanged(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)

        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        binding.tvDate.text = formatter.format(calendar.time)
    }
}