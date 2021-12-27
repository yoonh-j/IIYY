package com.yoond.iiyy.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yoond.iiyy.data.Comment
import com.yoond.iiyy.data.Community
import com.yoond.iiyy.data.CommunityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val repository: CommunityRepository
): ViewModel() {

    fun getArticle(key: String): MutableLiveData<Community> =
        repository.getArticle(key)

    fun getAllArticles(): LiveData<List<Community>> =
        repository.getAllArticles()

    fun getAllComments(key: String): LiveData<List<Comment>> =
        repository.getAllComments(key)

    fun getImageUri(key: String): LiveData<Uri> =
        repository.getImageUri(key)

    fun getNewArticleKey()  = repository.getNewArticleKey()

    fun getNewCommentKey(articleKey: String) = repository.getNewCommentKey(articleKey)

    fun deleteArticle(article: Community) =
        repository.deleteArticle(article)

    fun insertArticle(article: Community) =
        repository.insertArticle(article)

    fun insertImage(key: String, data: ByteArray) =
        repository.insertImage(key, data)

    fun insertComment(articleKey: String, commentKey: String, comment: Comment) =
        repository.insertComment(articleKey, commentKey, comment)
}