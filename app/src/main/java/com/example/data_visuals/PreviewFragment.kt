package com.example.data_visuals

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import java.util.*

class PreviewFragment : Fragment() {
    private lateinit var pickDateButton: Button
    private lateinit var pickTimeButton: Button
    private lateinit var toggleButton: ToggleButton
    private lateinit var setAlarmButton: Button
    private lateinit var alarmStatusTextView: TextView
    private lateinit var alarmManager: AlarmManager
    private var pendingIntent: PendingIntent? = null
    private var selectedDate: String = ""
    private var selectedTime: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_preview, container, false)

        pickDateButton = view.findViewById(R.id.pickDateButton)
        pickTimeButton = view.findViewById(R.id.pickTimeButton)
        toggleButton = view.findViewById(R.id.toggleButton)
        setAlarmButton = view.findViewById(R.id.setAlarmButton)
        alarmStatusTextView = view.findViewById(R.id.alarmStatusTextView)

        alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        pickDateButton.setOnClickListener {
            showDatePicker()
        }

        pickTimeButton.setOnClickListener {
            showTimePicker()
        }

        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setAlarmButton.isEnabled = true
            } else {
                setAlarmButton.isEnabled = false
                cancelAlarm()
            }
        }

        setAlarmButton.setOnClickListener {
            setAlarm()
        }

        return view
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                selectedDate = "$dayOfMonth/${month + 1}/$year"
                pickDateButton.text = selectedDate
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                selectedTime = "$hourOfDay:$minute"
                pickTimeButton.text = selectedTime
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }

    private fun setAlarm() {
        // Implement set alarm logic using selectedDate and selectedTime
    }

    private fun cancelAlarm() {
        // Implement cancel alarm logic
    }
}
