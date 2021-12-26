package com.yoond.iiyy.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yoond.iiyy.MainActivity
import com.yoond.iiyy.R
import com.yoond.iiyy.adapters.CommunityListAdapter
import com.yoond.iiyy.databinding.FragmentCommunityBinding
import com.yoond.iiyy.decorators.ListDecoration
import com.yoond.iiyy.viewmodels.CommunityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment : Fragment() {

    private lateinit var binding: FragmentCommunityBinding
    private val viewModel: CommunityViewModel by viewModels()

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
        subscribeUi(adapter)

        val default = resources.getDimension(R.dimen.list_item_margin).toInt()
        val last = resources.getDimension(R.dimen.list_item_last_margin).toInt()
        binding.comRecycler.addItemDecoration(ListDecoration(default, last))
        binding.comFab.setOnClickListener {
            navigateToCommunityWrite()
        }
        (activity as MainActivity).setBackButtonVisible(false)
    }

    private fun subscribeUi(adapter: CommunityListAdapter) {
        viewModel.getAllArticles().observe(viewLifecycleOwner) { articles ->
            binding.hasItems = !articles.isNullOrEmpty()
            adapter.submitList(articles) {
                binding.comRecycler.invalidateItemDecorations()
            }
        }
    }

    private fun navigateToCommunityWrite() {
        findNavController().navigate(R.id.action_community_fragment_to_community_write_fragment)
    }
}