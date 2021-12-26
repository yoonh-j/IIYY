package com.yoond.iiyy.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Query
import com.yoond.iiyy.data.Community
import com.yoond.iiyy.data.CommunityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val repository: CommunityRepository
): ViewModel() {
    private var allArticles: MutableLiveData<List<Community>> = MutableLiveData()

    fun getAllArticles(): LiveData<List<Community>> {
        repository.getAllArticles()
            .orderBy("timeInMillis", Query.Direction.DESCENDING) // 등록 시간 역순으로 가져옴
            .addSnapshotListener { value, error ->
            if (error != null) {
                Log.e(TAG, "Listen failed", error)
                allArticles.value = null

                return@addSnapshotListener
            }
            val list = mutableListOf<Community>()
            if (value != null) {
                for (doc in value) {
                    val item = doc.toObject(Community::class.java)
                    list.add(item)
                }
                allArticles.value = list
            }
        }
        return allArticles
    }

    fun deleteArticle(article: Community) {
        repository.deleteArticle(article)
    }

    fun insertArticle(article: Community) {
        repository.insertArticle(article)
    }

    companion object {
        private const val TAG = "COMMUNITY_VIEW_MODEL"
    }
}