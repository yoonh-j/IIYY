package com.yoond.iiyy.views

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.yoond.iiyy.MainActivity
import com.yoond.iiyy.R
import com.yoond.iiyy.data.Community
import com.yoond.iiyy.databinding.FragmentCommunityWriteBinding
import com.yoond.iiyy.utils.REQUEST_COMMUNITY_IMAGE
import com.yoond.iiyy.viewmodels.AuthViewModel
import com.yoond.iiyy.viewmodels.CommunityViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.util.*

@AndroidEntryPoint
class CommunityWriteFragment : Fragment() {
    private lateinit var binding: FragmentCommunityWriteBinding

    private val authViewModel: AuthViewModel by viewModels()
    private val comViewModel: CommunityViewModel by viewModels()
    private val args: CommunityWriteFragmentArgs by navArgs()

    private var isImageSelected: Boolean = false
    private var isEditing: Boolean = false
    private lateinit var articleKey: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityWriteBinding.inflate(inflater, container, false)

        subscribeUi()
        if (isEditing) {
            initEdit()
        } else {
            init()
        }
        setHasOptionsMenu(true)
        (activity as MainActivity).setBackButtonVisible(true)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // 새 글 작성 모드일 때만 타이틀 설정
        if (isEditing) {
            (activity as MainActivity).setToolbarTitle("")
        } else {
            (activity as MainActivity).setToolbarTitle(resources.getString(R.string.title_community_write))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_COMMUNITY_IMAGE &&
            resultCode == RESULT_OK &&
            data != null) {
            binding.comWriteImage.setImageURI(data.data)
            setImageSelected(true)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar_com_write, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        // 편집 모드일 때 사진 선택 불가
        if (isEditing) {
            menu.findItem(R.id.menu_com_write_clip).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_com_write_clip -> {
                selectPicture()
                true
            }
            R.id.menu_com_write_done -> {
                // firebase에 업로드 후 종료
                if (isEditing) {
                    uploadEdition()
                } else {
                    upload()
                }
                (activity as MainActivity).hideKeyboard()
                navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * 수정할 글의 key가 있으면 편집 모드, 없으면 새 글 작성 모드
     */
    private fun subscribeUi() {
        articleKey = args.articleKey

        if (articleKey == "") {
            binding.article = null
            isEditing = false
        } else {
            comViewModel.getArticle(articleKey).observe(viewLifecycleOwner) { article ->
                binding.article = article
            }
            comViewModel.getImageUri(articleKey).observe(viewLifecycleOwner) { uri ->
                if (uri == null) {
                    setImageSelected(false)
                } else {
                    Glide.with(this)
                        .load(uri)
                        .into(binding.comWriteImage)
                }
            }
            isEditing = true
        }
    }

    /**
     * 새 글 작성 모드일 때 초기화
     */
    private fun init() {
        binding.comWriteImage.isVisible = false
        binding.comWriteImageDelete.isVisible = false
        binding.setClickListener {
            setImageSelected(false)
        }
    }

    /**
     * 수정 모드일 때 초기화
     */
    private fun initEdit() {
        binding.comWriteImageDelete.isVisible = false
    }

    private fun selectPicture() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_COMMUNITY_IMAGE)
    }

    private fun setImageSelected(isSelected: Boolean) {
        binding.comWriteImage.isVisible = isSelected
        binding.comWriteImageDelete.isVisible = isSelected
        isImageSelected = isSelected
    }

    private fun upload() {
        val title = binding.comWriteTitle.text.toString()
        val content = binding.comWriteContent.text.toString()

        if (title == "") {
            Toast.makeText(context, getString(R.string.com_write_no_title), Toast.LENGTH_LONG).show()
        } else if (content == "") {
            Toast.makeText(context, getString(R.string.com_no_content), Toast.LENGTH_LONG).show()
        } else {
            val user = authViewModel.getUser().value

            if (user != null) {
                val uid = user.uid
                val key = comViewModel.getNewArticleKey()
                val timeInMillis = Calendar.getInstance().timeInMillis
                comViewModel.insertArticle(
                    Community(key, uid, title, content, timeInMillis)
                )
                if (isImageSelected) uploadImage(key)
            }
        }
    }

    private fun uploadImage(key: String)  {
        val bitmap = (binding.comWriteImage.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos)
        val data = baos.toByteArray()

        comViewModel.insertImage(key, data)
    }

    private fun uploadEdition() {
        val user = authViewModel.getUser().value
        val article = binding.article as Community

        if (user != null) {
            article.title = binding.comWriteTitle.text.toString()
            article.content = binding.comWriteContent.text.toString()

            comViewModel.insertArticle(article)
        }
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }
}