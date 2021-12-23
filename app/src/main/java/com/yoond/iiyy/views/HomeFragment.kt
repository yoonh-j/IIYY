package com.yoond.iiyy.views

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yoond.iiyy.MainActivity
import com.yoond.iiyy.R
import com.yoond.iiyy.adapters.SupplementAdapter
import com.yoond.iiyy.data.Supplement
import com.yoond.iiyy.databinding.FragmentHomeBinding
import com.yoond.iiyy.viewmodels.SupplementListViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 */
@AndroidEntryPoint
class HomeFragment : Fragment(), SupplementAdapter.OnDeleteClickListener, SupplementAdapter.OnCheckClickListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: SupplementListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val adapter = SupplementAdapter(
            deleteClickListener = this,
            checkClickListener = this
        )
        binding.homeRecycler.adapter = adapter
        subscribeUi(adapter)

//        setHasOptionsMenu(true)
        (activity as MainActivity).setBackButtonVisible(false)
        return binding.root
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu_toolbar_home, menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.menu_all_supplements -> {
//                navigateToHomeAll()
//                true
//            } else -> super.onOptionsItemSelected(item)
//        }
//    }

    override fun onCheckClick(supplement: Supplement) {
        viewModel.updateSupplement(supplement)
    }

    override fun onDeleteClick(supplement: Supplement) {
        viewModel.deleteSupplement(supplement)
    }

    private fun subscribeUi(adapter: SupplementAdapter) {
        viewModel.supplements.observe(viewLifecycleOwner) { supplements ->
            binding.hasItems = !supplements.isNullOrEmpty()
            adapter.submitList(supplements)
        }
    }
//
//    private fun navigateToHomeAll() {
//        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToHomeAllFragment())
//    }
}