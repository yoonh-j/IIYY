package com.yoond.iiyy.views

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yoond.iiyy.MainActivity
import com.yoond.iiyy.R
import com.yoond.iiyy.data.Community
import com.yoond.iiyy.databinding.FragmentCommunityWriteBinding
import com.yoond.iiyy.utils.REQUEST_COMMUNITY_IMAGE
import com.yoond.iiyy.viewmodels.CommunityViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.util.*

@AndroidEntryPoint
class CommunityWriteFragment : Fragment() {
    private lateinit var binding: FragmentCommunityWriteBinding
    private val viewModel: CommunityViewModel by viewModels()
    private var isImageSelected: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityWriteBinding.inflate(inflater, container, false)

        init()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarTitle(resources.getString(R.string.title_community_write))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_COMMUNITY_IMAGE && resultCode == RESULT_OK) {
            binding.comWriteImage.setImageURI(data?.data)
            setSelectedImage(true)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar_com_write, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_com_write_clip -> {
                selectPicture()
                true
            }
            R.id.menu_com_write_done -> {
                upload()
                // firebase에 업로드 후 종료
                navigateUp()
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }

    private fun init() {
        setHasOptionsMenu(true)
        (activity as MainActivity).setBackButtonVisible(true)

        binding.comWriteImage.isVisible = false
        binding.comWriteImageDelete.isVisible = false
        binding.setClickListener {
            setSelectedImage(false)
        }
    }

    private fun selectPicture() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_COMMUNITY_IMAGE)
    }

    private fun setSelectedImage(isSelected: Boolean) {
        binding.comWriteImage.isVisible = isSelected
        binding.comWriteImageDelete.isVisible= isSelected
        isImageSelected = isSelected
    }

    private fun upload() {
        val title = binding.comWriteTitle.text.toString()
        val content = binding.comWriteContent.text.toString()

        if (title == "") {
            Toast.makeText(context, getString(R.string.com_write_no_title), Toast.LENGTH_LONG).show()
        } else if (content == "") {
            Toast.makeText(context, getString(R.string.com_write_no_content), Toast.LENGTH_LONG).show()
        } else {
            // TODO: get uid from firebase auth
            val uid = "5"
            val key = viewModel.getNewKey()
            val timeInMillis = Calendar.getInstance().timeInMillis
            viewModel.insertArticle(
                Community(key, uid, title, content, timeInMillis)
            )
            if (isImageSelected) uploadImage(key)
        }
    }

    private fun uploadImage(key: String)  {
        val bitmap = (binding.comWriteImage.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos)
        val data = baos.toByteArray()

        viewModel.insertImage(key, data)
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }
}