package com.vm.timemanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.vm.timemanager.adapter.AdapterDays
import com.vm.timemanager.databinding.FragmentDaysBinding
import com.vm.timemanager.viewModel.DaysViewModel

class DaysFragmentList : Fragment() {

    private var _binding: FragmentDaysBinding? = null
    private val binding get() = _binding!!
//    val model by activityViewModels<DaysViewModel>()

    //private val dayName: String = "Monday"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //val view =  inflater.inflate(R.layout.fragment_day, container, false)

        _binding = FragmentDaysBinding.inflate(inflater, container, false)
        val view = binding.root
//        val viewModelFactory = this.context?.let { DaysViewModelFactory(dayName, it) }
        val viewModel = ViewModelProvider(this)[DaysViewModel::class.java]
//        val view = activityViewModels<DaysViewModel> ()

        //receive data from SevenDaysFragment
        viewModel.dayName = DaysFragmentListArgs.fromBundle(requireArguments()).dayName

//        viewModel.day = dayName
        //send data to NewTaskFragment
        val adapterDays = AdapterDays() { taskId: Int ->

            //send the data to the NewTaskFragment for changing some task
            val action = DaysFragmentListDirections.sendIdToNewTaskFragment(id = taskId, day = viewModel.dayName)
            view.findNavController().navigate(action)
        }

        with(binding.taskList) {
            adapter = adapterDays
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))

            //improve performance the RecyclerView if we know that changes in content don't change the layout size
            setHasFixedSize(true)
        }

        viewModel.getAllTasks()

        //add a TaskList to the adapter if the LiveData allTask changes
        viewModel.allTasks.observe(viewLifecycleOwner, Observer { taskList ->
            adapterDays.submitList(taskList.sortedBy { it.startTime?.hour })
        })

        binding.newTaskFab.setOnClickListener {
            //send the data to the NewTaskFragment for creating a new task
            val action = DaysFragmentListDirections.dayFragmentToNewTaskFragment(viewModel.dayName, 0)
            view.findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}