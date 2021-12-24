package com.yoond.iiyy.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.google.android.material.color.MaterialColors
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.yoond.iiyy.MainActivity
import com.yoond.iiyy.R
import com.yoond.iiyy.databinding.FragmentCalendarBinding
import com.yoond.iiyy.decorators.CalendarSelectDecorator
import com.yoond.iiyy.decorators.CalendarStateDecorator
import com.yoond.iiyy.decorators.CalendarTodayDecorator
import com.yoond.iiyy.utils.TAG_CALENDAR_DETAIL_DIALOG
import com.yoond.iiyy.viewmodels.CalendarViewModel
import com.yoond.iiyy.viewmodels.SupplementListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/**
 *
 */
@AndroidEntryPoint
class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding
    private val calViewModel: CalendarViewModel by viewModels()
    private val supViewModel: SupplementListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        initCalendar()
        subscribeUi()
        (activity as MainActivity).setBackButtonVisible(false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarTitle(resources.getString(R.string.title_calendar))
    }

    private fun subscribeUi() {
        calViewModel.states.observe(viewLifecycleOwner) { states ->
            if (!states.isNullOrEmpty()) {
                calViewModel.getStateLists(states)
                setCalendarStates()
            }
        }
    }

    private fun initCalendar() {
        binding.calendarCal.setOnDateChangedListener { _, date, _ ->
            val dateInMillis = getSelectedDateInMillis(date)

            CoroutineScope(Dispatchers.Main).launch {
                val supplements = supViewModel.getSupplementsByTimeInMillis(dateInMillis)
                if (!supplements.isNullOrEmpty()) {
                    CalendarDetailDialog(dateInMillis, supplements)
                        .show(childFragmentManager, TAG_CALENDAR_DETAIL_DIALOG)
                }
            }
        }
        binding.calendarCal.setTitleFormatter {
            "${it.year}년 ${it.month}월"
        }
    }

    private fun setCalendarStates() {
        val selectDrawable = ResourcesCompat.getDrawable(resources, R.drawable.bg_cal_select, null)
        val dotRadius = ResourcesCompat.getFloat(resources, R.dimen.item_cal_dot_radius)
        val todaySize = ResourcesCompat.getFloat(resources, R.dimen.item_cal_today_size)

        val textColor = MaterialColors.getColor(requireView(), R.attr.colorOnSurface)
        val todayColor = MaterialColors.getColor(requireView(), R.attr.colorPrimary)

        val allTakenColor = resources.getColor(R.color.green_500, null)
        val partiallyTakenColor = resources.getColor(R.color.yellow_600, null)
        val notTakenColor = resources.getColor(R.color.red_500, null)

        binding.calendarCal.addDecorators(
            selectDrawable?.let { CalendarSelectDecorator(it, textColor) },
            CalendarStateDecorator(calViewModel.allTakenList, dotRadius, allTakenColor),
            CalendarStateDecorator(calViewModel.partiallyTakenList, dotRadius, partiallyTakenColor),
            CalendarStateDecorator(calViewModel.notTakenList, dotRadius, notTakenColor),
            CalendarTodayDecorator(todaySize, todayColor)
        )
    }

    private fun getSelectedDateInMillis(date: CalendarDay): Long {
        val cal = Calendar.getInstance()
        cal.set(date.year, date.month - 1, date.day, 0, 0, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }
}