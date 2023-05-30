package com.vm.timemanager.fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import java.time.LocalDateTime

class TimePickerFragment: DialogFragment() {

    private val args: TimePickerFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)

        val date = args.taskTime
        val initialYear = date.year
        val initialMonth = date.month.value
        val initialDay = date.dayOfMonth
        val initialHour = date.hour
        val initialMinutes = date.minute

        val timeListener = TimePickerDialog.OnTimeSetListener {
                _: TimePicker, hours: Int, minutes: Int ->

            val resultTime: LocalDateTime = LocalDateTime.of(initialYear, initialMonth, initialDay, hours, minutes)
            setFragmentResult(REQUEST_START_TIME, bundleOf(BUNDLE_KEY_TIME to resultTime))
        }

        return TimePickerDialog(
            requireContext(),
            timeListener,
            initialHour,
            initialMinutes,
            true
        )
    }

    companion object {
        const val REQUEST_START_TIME = "REQUEST_START_TIME"
        const val BUNDLE_KEY_TIME = "BUNDLE_KEY_TIME"
    }
}
