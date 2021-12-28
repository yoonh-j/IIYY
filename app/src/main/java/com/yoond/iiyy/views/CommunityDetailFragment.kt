package com.yoond.iiyy.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.yoond.iiyy.MainActivity
import com.yoond.iiyy.R
import com.yoond.iiyy.adapters.CommentListAdapter
import com.yoond.iiyy.data.Comment
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityDetailBinding.inflate(inflater, container, false)

        init()
        return binding.root
    }

    private fun init() {
        subscribeUi()
        binding.setClickListener {
            uploadComment()
        }
        (activity as MainActivity).setBackButtonVisible(true)
    }

    private fun subscribeUi() {
        val key = args.articleKey

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
}