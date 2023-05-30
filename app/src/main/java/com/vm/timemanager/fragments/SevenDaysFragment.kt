package com.vm.timemanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.vm.timemanager.R
import com.vm.timemanager.databinding.FragmentSevenDaysBinding

class SevenDaysFragment : Fragment() {

    private var _binding: FragmentSevenDaysBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         _binding = FragmentSevenDaysBinding.inflate(inflater, container, false)

        binding.apply {
            val days: Map<String, Button> = mapOf(
                getString(R.string.monday) to monday,
                getString(R.string.tuesday) to tuesday,
                getString(R.string.wednesday) to wednesday,
                getString(R.string.thursday) to thursday,
                getString(R.string.friday) to friday,
                getString(R.string.saturday) to saturday,
                getString(R.string.sunday) to sunday
            )

            days.forEach { (key, value) ->
                value.setOnClickListener {
                    transferDay(key)
                }
            }
        }
        return binding.root
    }

    private fun transferDay(day: String) {
        val action = SevenDaysFragmentDirections.actionSevenDaysFragmentToDaysFragment(day)
        binding.root.findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}