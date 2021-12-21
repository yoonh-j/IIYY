package com.yoond.iiyy.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yoond.iiyy.adapters.SupplementAdapter
import com.yoond.iiyy.databinding.FragmentHomeBinding
import com.yoond.iiyy.viewmodels.SupplementListViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: SupplementListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        val adapter = SupplementAdapter()
        binding.homeRecycler.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: SupplementAdapter) {
        viewModel.supplements.observe(viewLifecycleOwner) { supplements ->
            adapter.submitList(supplements)
        }
    }
}