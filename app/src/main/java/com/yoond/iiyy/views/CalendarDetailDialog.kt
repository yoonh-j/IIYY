package com.yoond.iiyy.views

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import com.yoond.iiyy.R
import com.yoond.iiyy.adapters.CalendarDetailAdapter
import com.yoond.iiyy.data.Supplement
import com.yoond.iiyy.databinding.DialogCalendarDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class CalendarDetailDialog(
    private val dateInMillis: Long,
    private val supplements: List<Supplement>
): DialogFragment(), View.OnClickListener {
    private lateinit var binding: DialogCalendarDetailBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context, R.style.Theme_IIYY_Dialog)

        binding = DialogCalendarDetailBinding.inflate(layoutInflater)
        binding.date =  SimpleDateFormat("yyyy년 M월 d일", Locale.KOREA).format(dateInMillis)
        binding.clickListener = this

        val adapter = CalendarDetailAdapter()
        subscribeUi(adapter)
        binding.dialogCalDetailRecycler.adapter = adapter

        return builder.setView(binding.root).create()
    }

    override fun onResume() {
        super.onResume()
        setDialogHeight()
        setDialogBackground()
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.dialog_cal_detail_close) dialog?.dismiss()
    }

    private fun subscribeUi(adapter: CalendarDetailAdapter) {
        adapter.submitList(supplements)
    }

    private fun setDialogHeight() {
        val params = dialog?.window?.attributes
        val ratio = ResourcesCompat.getFloat(resources, R.dimen.dialog_height_ratio)
        params?.height = (resources.displayMetrics.heightPixels * ratio).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    private fun setDialogBackground() {
        // 배경을 투명으로 설정해야 커스텀 배경이 보임
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
    }
}