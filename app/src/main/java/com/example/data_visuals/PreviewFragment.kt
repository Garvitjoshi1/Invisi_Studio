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
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.*

class PreviewFragment : Fragment() {
    private lateinit var datePicker: DatePicker
    private lateinit var timePicker: TimePicker
    private lateinit var pickDateButton: Button
    private lateinit var pickTimeButton: Button
    private lateinit var setAlarmButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_preview, container, false)

        datePicker = view.findViewById(R.id.datePicker)
        timePicker = view.findViewById(R.id.timePicker)
        pickDateButton = view.findViewById(R.id.pickDateButton)
        pickTimeButton = view.findViewById(R.id.pickTimeButton)
        setAlarmButton = view.findViewById(R.id.setAlarmButton)

        pickDateButton.setOnClickListener {
            // Show DatePickerDialog
            showDatePicker()
        }

        pickTimeButton.setOnClickListener {
            // Show TimePickerDialog
            showTimePicker()
        }

        setAlarmButton.setOnClickListener {
            // Set the alarm
            setAlarm()
        }

        return view
    }

    private fun showDatePicker() {
        // Show DatePickerDialog
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            datePicker.updateDate(year, month, dayOfMonth)
        }, year, month, day)
        datePickerDialog.show()
    }

    private fun showTimePicker() {
        // Show TimePickerDialog
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
            timePicker.hour = hourOfDay
            timePicker.minute = minute
        }, hour, minute, true)
        timePickerDialog.show()
    }

    private fun setAlarm() {
        // Get the selected date and time
        val selectedYear = datePicker.year
        val selectedMonth = datePicker.month
        val selectedDayOfMonth = datePicker.dayOfMonth
        val selectedHourOfDay = timePicker.hour
        val selectedMinute = timePicker.minute

        // Create a Calendar instance and set the selected date and time
        val calendar = Calendar.getInstance().apply {
            set(selectedYear, selectedMonth, selectedDayOfMonth, selectedHourOfDay, selectedMinute)
        }

        // Get the AlarmManager service
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Create an Intent for the BroadcastReceiver
        val intent = Intent(requireContext(), AlarmReceiver::class.java)

        // Create a PendingIntent to be triggered when the alarm goes off
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent,
            PendingIntent.FLAG_IMMUTABLE)

        // Set the alarm
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        // Display a toast message showing the selected date and time
        val message = "Alarm is set for $selectedDayOfMonth/${selectedMonth + 1}/$selectedYear at $selectedHourOfDay:$selectedMinute"
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}
