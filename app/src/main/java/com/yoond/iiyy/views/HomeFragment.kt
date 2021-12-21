package com.yoond.iiyy.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoond.iiyy.adapters.SupplementAdapter
import com.yoond.iiyy.databinding.FragmentHomeBinding

/**
 *
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        val adapter = SupplementAdapter()
        binding.homeRecycler.adapter = adapter

        return binding.root
    }
}