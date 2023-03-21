package com.example.day_12.dialogfragment

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment: DialogFragment(), TimePickerDialog.OnTimeSetListener {
    var timeChangedListener: SetOnTimeChanged? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        val is24HourCycle = false
        return TimePickerDialog(activity, this, hour, minute, is24HourCycle)
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        timeChangedListener?.onTimeChanged(hourOfDay, minute)
    }

    interface SetOnTimeChanged {
        fun onTimeChanged(hourOfDay: Int, minute: Int)
    }

}