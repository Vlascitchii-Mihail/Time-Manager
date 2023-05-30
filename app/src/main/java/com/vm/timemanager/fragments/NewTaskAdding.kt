package com.vm.timemanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.vm.timemanager.R
import com.vm.timemanager.alarm.AlarmSchedulerImpl
import com.vm.timemanager.data.Task
import com.vm.timemanager.databinding.FragmentNewTaskAddingBinding
import com.vm.timemanager.viewModel.NewTaskAddingViewModel
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

/**
 * Adding a new Task
 */
class NewTaskAdding : Fragment() {

    private var _binding: FragmentNewTaskAddingBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<NewTaskAddingViewModel>()
    private val args: NewTaskAddingArgs by navArgs()
    private val alarmSchedulerImpl: AlarmSchedulerImpl? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_new_task_adding, container, false)
        _binding = FragmentNewTaskAddingBinding.inflate(inflater, container, false)

        binding.toolbar.inflateMenu(R.menu.toolbars_menu)
        binding.toolbar.title = args.day + " "

        //reference to the navigation controller
        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        //connect appBarConfiguration with nav_graph
        val builder = AppBarConfiguration.Builder(navController.graph)
        val appBarConfiguration = builder.build()

        //applies the configuration to the toolbar
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        viewModel.day = args.day
        //todoȘ optimise
        val startDate = getDayDate(args.day)

        //choice to load existing task or to create new
        binding.apply {
            if (args.id != 0) {
                viewModel.getTaskFlow(args.id)
                addButton.visibility = View.GONE
            }
            else {
                viewModel.getNewTask()
                saveButton.visibility = View.GONE
            }

            viewModelBind = viewModel
            lifecycleOwner = viewLifecycleOwner

            buttonStartTime.setOnClickListener {
                findNavController().navigate(
                    NewTaskAddingDirections.selectTime(startDate, TimePickerFragment.REQUEST_START_TIME)
                )
            }

            addButton.setOnClickListener {
                val inputTask = Task(
                    day = viewModel.day,
                    startTime = viewModel.taskFlow.value.startTime ?: startDate,
                    taskName = viewModel.taskFlow.value.taskName,
                    taskDescription = viewModel.taskFlow.value.taskDescription
                )
                viewModel.addTask(inputTask)
                navController.popBackStack()

                //set alarm
                AlarmSchedulerImpl(
                    context,
                    inputTask
                ).setAlarm()
            }

            saveButton.setOnClickListener {
                viewModel.taskFlow.value.let {
                    viewModel.updateTask(it)

                    //set alarm
                    AlarmSchedulerImpl(
                        context,
                        it
                    ).setAlarm()
                    navController.popBackStack()
                }
            }

            toolbar.setOnMenuItemClickListener {
               when (it.itemId) {
                   R.id.delete_a_task -> {
                       viewModel.deleteTask()
                       //delete alarm
                       alarmSchedulerImpl?.cancelAlarm(viewModel.taskFlow.value)
                       navController.popBackStack()
                   true
                   }
                   else -> false
               }
            }

            binding.startDate.text = startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy/HH:mm"))
        }

        setFragmentResultListener(TimePickerFragment.REQUEST_START_TIME) { _, bundle ->
            viewModel.taskFlow.value.startTime = bundle.getSerializable(TimePickerFragment.BUNDLE_KEY_TIME) as LocalDateTime
//            Toast.makeText(requireContext(), "Date:  ${viewModel.task.value}", Toast.LENGTH_SHORT).show()
//            this.activity?.recreate()
        }
        return binding.root
    }

    private fun getDayDate(day: String?): LocalDateTime {
        val currentDate = LocalDateTime.now()
        return when (day) {
            getString(R.string.monday) -> currentDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))
            getString(R.string.tuesday) -> currentDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY))
            getString(R.string.wednesday) -> currentDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY))
            getString(R.string.thursday) -> currentDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY))
            getString(R.string.friday) -> currentDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY))
            getString(R.string.saturday) -> currentDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))
            else -> currentDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
        }
    }
}