package com.yoond.iiyy.views

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.yoond.iiyy.MainActivity
import com.yoond.iiyy.R
import com.yoond.iiyy.adapters.CommentListAdapter
import com.yoond.iiyy.data.Comment
import com.yoond.iiyy.data.Community
import com.yoond.iiyy.databinding.FragmentCommunityDetailBinding
import com.yoond.iiyy.viewmodels.AuthViewModel
import com.yoond.iiyy.viewmodels.CommunityViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CommunityDetailFragment : Fragment() {
    private lateinit var binding: FragmentCommunityDetailBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val comViewModel: CommunityViewModel by viewModels()
    private val args: CommunityDetailFragmentArgs by navArgs()
    private var articleUid: String = ""
    private var uid: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityDetailBinding.inflate(inflater, container, false)

        init()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarTitle("")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (uid == articleUid) {
            inflater.inflate(R.menu.menu_toolbar_com_detail, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_edit -> {
                navigateToWrite()
                return true
            }
            R.id.menu_delete -> {
                comViewModel.deleteArticle((binding.article as Community))
                findNavController().navigateUp()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun init() {
        subscribeUi()
        articleUid = args.articleUid
        binding.setClickListener {
            uploadComment()
        }
        setHasOptionsMenu(true)
        (activity as MainActivity).setBackButtonVisible(true)
    }

    private fun subscribeUi() {
        val key = args.articleKey

        authViewModel.getUser().observe(viewLifecycleOwner) { user ->
            uid = user.uid
        }
        comViewModel.getArticle(key).observe(viewLifecycleOwner) { article ->
            binding.article = article
        }
        comViewModel.getAllComments(key).observe(viewLifecycleOwner) { comments ->
            if (comments == null) {
                binding.hasComment = false
            } else {
                binding.hasComment = true
                setAdapter(comments)
            }
        }
        comViewModel.getImageUri(key).observe(viewLifecycleOwner) { uri ->
            if (uri == null) {
                binding.hasImage = false
            } else {
                binding.hasImage = true
                Glide.with(this)
                    .load(uri)
                    .into(binding.comDetailImage)
            }
        }
    }

    private fun setAdapter(comments: List<Comment>) {
        val adapter = CommentListAdapter()
        binding.comDetailRecycler.adapter = adapter
        adapter.submitList(comments)
    }

    private fun uploadComment() {
        val comment = binding.comDetailCommentInput.text.toString()

        if (comment == "") {
            Toast.makeText(context, resources.getString(R.string.com_no_content), Toast.LENGTH_SHORT).show()
        } else {
            val user = authViewModel.getUser().value

            if (user != null) {
                val uid = user.uid
                val articleKey = args.articleKey
                val key = comViewModel.getNewCommentKey(articleKey)
                val timeInMillis = Calendar.getInstance().timeInMillis

                comViewModel.insertComment(articleKey, key, Comment(key, uid, comment, timeInMillis))
            }

            binding.comDetailCommentInput.setText("")
            (activity as MainActivity).hideKeyboard()
        }
    }

    private fun navigateToWrite() {
        val destination = CommunityDetailFragmentDirections
            .actionCommunityDetailFragmentToCommunityWriteFragment(
                (binding.article as Community).key
            )
        findNavController().navigate(destination)
    }
}