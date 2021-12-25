package com.yoond.iiyy.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoond.iiyy.MainActivity
import com.yoond.iiyy.R
import com.yoond.iiyy.adapters.CommunityListAdapter
import com.yoond.iiyy.data.Community
import com.yoond.iiyy.databinding.FragmentCommunityBinding
import com.yoond.iiyy.decorators.ListDecoration
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class CommunityFragment : Fragment() {

    private lateinit var binding: FragmentCommunityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityBinding.inflate(inflater, container, false)

        init()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarTitle(resources.getString(R.string.title_community))
    }

    private fun init() {
        val adapter = CommunityListAdapter()
        binding.comRecycler.adapter = adapter
        adapter.submitList(getItems())
        binding.hasItems = true

        val default = resources.getDimension(R.dimen.list_item_margin).toInt()
        val last = resources.getDimension(R.dimen.list_item_last_margin).toInt()
        binding.comRecycler.addItemDecoration(ListDecoration(default, last))
        binding.comFab.setOnClickListener {
            navigateToCommunityWrite()
        }
        (activity as MainActivity).setBackButtonVisible(false)
    }

    private fun getItems(): List<Community> {
        // TODO: get items from firebase
        return dummyItems()
    }

    private fun dummyItems() = listOf(
        Community("1", "1", "Eleven", "아이브", 1640445007000),
        Community("2", "1", "나비효과", "볼빨간사춘기", 1640491807000),
        Community("3", "2", "Last Christmas", "Ariana Grande", 1640404800000),
        Community("4", "3", "Off My Face", "Justin Bieber", 1640289600000),
        Community("5", "4", "어떻게 이별까지 사랑하겠어, 널 사랑하는 거지", "악동뮤지션", 1640252100000),
        Community("6", "5", "신호등", "이무진", 1640165700000),
        Community("7", "5", "All Too Well (10 Minute Version) (Taylor's Version) (From The Vault)", "Taylor Swift", 1640079300000)
    )

    private fun navigateToCommunityWrite() {
        // TODO: navigateToCommunityWrite
    }
}