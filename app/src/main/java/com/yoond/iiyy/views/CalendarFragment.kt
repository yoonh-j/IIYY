package com.yoond.iiyy.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.google.android.material.color.MaterialColors
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.yoond.iiyy.MainActivity
import com.yoond.iiyy.R
import com.yoond.iiyy.databinding.FragmentCalendarBinding
import com.yoond.iiyy.decorators.CalendarSelectDecorator
import com.yoond.iiyy.decorators.CalendarStateDecorator
import com.yoond.iiyy.decorators.CalendarTodayDecorator
import com.yoond.iiyy.viewmodels.CalendarViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

/**
 *
 */
@AndroidEntryPoint
class CalendarFragment : Fragment(), OnDateSelectedListener {
    private lateinit var binding: FragmentCalendarBinding
    private val viewModel: CalendarViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        subscribeUi()
        (activity as MainActivity).setBackButtonVisible(false)
        return binding.root
    }

    override fun onDateSelected(
        widget: MaterialCalendarView,
        date: CalendarDay,
        selected: Boolean
    ) {
        val timeInMillis = getSelectedTimeInMillis(date)
        Toast.makeText(context, "onDateSelected:$timeInMillis", Toast.LENGTH_SHORT).show()
    }

    private fun subscribeUi() {
        viewModel.states.observe(viewLifecycleOwner) { states ->
            if (!states.isNullOrEmpty()) {
                viewModel.initTakenLists(states)
                initCalendar()
            }
        }
    }

    private fun initCalendar() {
        val selectDrawable = ResourcesCompat.getDrawable(resources, R.drawable.bg_cal_select, null)
        val dotRadius = ResourcesCompat.getFloat(resources, R.dimen.item_cal_dot_radius)
        val todaySize = ResourcesCompat.getFloat(resources, R.dimen.item_cal_today_size)

        val textColor = MaterialColors.getColor(requireView(), R.attr.colorOnSurface)
        val todayColor = MaterialColors.getColor(requireView(), R.attr.colorPrimary)

        val allTakenColor = resources.getColor(R.color.green_500, null)
        val partiallyTakenColor = resources.getColor(R.color.yellow_600, null)
        val notTakenColor = resources.getColor(R.color.red_500, null)

        binding.calendarCal.setOnDateChangedListener(this)
        binding.calendarCal.addDecorators(
            selectDrawable?.let { CalendarSelectDecorator(it, textColor) },
            CalendarStateDecorator(viewModel.allTakenList, dotRadius, allTakenColor),
            CalendarStateDecorator(viewModel.partiallyTakenList, dotRadius, partiallyTakenColor),
            CalendarStateDecorator(viewModel.notTakenList, dotRadius, notTakenColor),
            CalendarTodayDecorator(todaySize, todayColor)
        )
    }

    private fun getSelectedTimeInMillis(date: CalendarDay): Long {
        val cal = Calendar.getInstance()
        cal.set(date.year, date.month - 1, date.day, 0, 0, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }
}