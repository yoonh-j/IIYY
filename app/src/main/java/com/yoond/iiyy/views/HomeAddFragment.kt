package com.yoond.iiyy.views

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yoond.iiyy.MainActivity
import com.yoond.iiyy.R
import com.yoond.iiyy.data.Supplement
import com.yoond.iiyy.databinding.FragmentHomeAddBinding
import com.yoond.iiyy.viewmodels.SupplementListViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class HomeAddFragment : Fragment() {
    private lateinit var binding: FragmentHomeAddBinding
    private val viewModel: SupplementListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeAddBinding.inflate(inflater, container, false)

        init()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarTitle(resources.getString(R.string.title_home_add))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar_home_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_home_done -> {
                addSupplement()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun init() {
        (activity as MainActivity).setBackButtonVisible(true)
        setHasOptionsMenu(true)
    }

    private fun addSupplement() {
        // 사용자가 영양제 이름을 입력하지 않으면 입력하라는 토스트 메시지를 띄우고
        // 입력하면 db에 저장
        if (binding.homeAddInput.text.toString() == "") {
            Toast.makeText(context, getString(R.string.home_add_no_input), Toast.LENGTH_LONG).show()
        } else {
            val timeInMillis = getTimeInMillis()
            val supplement = Supplement(
                binding.homeAddInput.text.toString(),
                timeInMillis,
                false
            )
            viewModel.insertSupplement(supplement)
            // 새 영양제 저장 후 종료
            navigateUp()
        }
    }

    // 사용자가 설정한 시간의 밀리초를 가져옴
    private fun getTimeInMillis(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, binding.homeAddTimepicker.hour)
        cal.set(Calendar.MINUTE, binding.homeAddTimepicker.minute)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }
}