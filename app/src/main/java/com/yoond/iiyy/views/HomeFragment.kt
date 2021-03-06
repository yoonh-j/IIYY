package com.yoond.iiyy.views

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yoond.iiyy.MainActivity
import com.yoond.iiyy.R
import com.yoond.iiyy.adapters.SupplementAdapter
import com.yoond.iiyy.data.Supplement
import com.yoond.iiyy.databinding.FragmentHomeBinding
import com.yoond.iiyy.decorators.ListDecoration
import com.yoond.iiyy.viewmodels.SupplementListViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 */
@AndroidEntryPoint
class HomeFragment :
    Fragment(),
    SupplementAdapter.OnDeleteClickListener,
    SupplementAdapter.OnCheckClickListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: SupplementListViewModel by viewModels()
    private var pressedTimeInMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBackPressed()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        init()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarTitle(resources.getString(R.string.title_home))
    }

    override fun onCheckClick(supplement: Supplement) {
        viewModel.updateSupplement(supplement)
    }

    override fun onDeleteClick(supplement: Supplement) {
        showConfirmDialog(supplement)
    }

    private fun init() {
        val adapter = SupplementAdapter(
            deleteClickListener = this,
            checkClickListener = this
        )
        binding.homeRecycler.adapter = adapter
        subscribeUi(adapter)

        val default = resources.getDimension(R.dimen.list_item_margin).toInt()
        val last = resources.getDimension(R.dimen.list_item_last_margin).toInt()
        binding.homeRecycler.addItemDecoration(ListDecoration(default, last))
        binding.homeFab.setOnClickListener {
            navigateToHomeAdd()
        }
        (activity as MainActivity).setBackButtonVisible(false)
    }

    private fun subscribeUi(adapter: SupplementAdapter) {
        viewModel.supplements.observe(viewLifecycleOwner) { supplements ->
            binding.hasItems = !supplements.isNullOrEmpty()
            adapter.submitList(supplements) {
                // ??? ????????? ??????????????? ??? ????????? ???????????? ?????? ????????? ?????? ???????????? ??????
                // item decoration invalidate ????????? Runnable
                binding.homeRecycler.invalidateItemDecorations()
            }
        }
    }

    private fun showConfirmDialog(supplement: Supplement) {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.home_delete_confirm)
            .setPositiveButton(R.string.dialog_positive) { _, _ ->
                viewModel.deleteSupplement(supplement)
            }
            .setNegativeButton(R.string.dialog_neutral, null)
            .show()
    }

    private fun navigateToHomeAdd() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToHomeAddFragment())
    }

    // ??????????????? ??? ??? ???????????? ????????? ??????
    private fun setBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(this) {
            if (System.currentTimeMillis() - pressedTimeInMillis > 2000) {
                pressedTimeInMillis = System.currentTimeMillis()
                Toast.makeText(context, resources.getString(R.string.home_toast_back_pressed), Toast.LENGTH_SHORT).show()
            } else {
                activity?.finish()
            }
        }?.isEnabled
    }
}