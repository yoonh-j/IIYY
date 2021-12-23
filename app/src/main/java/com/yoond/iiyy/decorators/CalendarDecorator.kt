package com.yoond.iiyy.decorators

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan

class CalendarSelectDecorator(
    private val selectBg: Drawable,
    private val textColor: Int
): DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay?): Boolean = true

    override fun decorate(view: DayViewFacade?) {
        view?.setSelectionDrawable(selectBg)
        view?.addSpan(ForegroundColorSpan(textColor))
    }
}

class CalendarStateDecorator(
    private val days: MutableList<CalendarDay>,
    private val dotRadius: Float,
    private val dotColor: Int
): DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay?): Boolean = days.contains(day)

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(dotRadius, dotColor))
    }
}

class CalendarTodayDecorator(
    private val todaySize: Float,
    private val textColor: Int
): DayViewDecorator {
    private val today = CalendarDay.today()

    override fun shouldDecorate(day: CalendarDay?): Boolean = today == day

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(StyleSpan(Typeface.BOLD))
        view?.addSpan(RelativeSizeSpan(todaySize))
        view?.addSpan(ForegroundColorSpan(textColor))
    }
}