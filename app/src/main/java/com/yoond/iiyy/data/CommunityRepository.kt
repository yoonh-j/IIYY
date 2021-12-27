package com.yoond.iiyy.data

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yoond.iiyy.utils.COMMENT
import com.yoond.iiyy.utils.COMMUNITY
import com.yoond.iiyy.utils.TIME_IN_MILLIS
import com.yoond.iiyy.viewmodels.CommunityViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommunityRepository @Inject constructor() {
    private val communityRef = Firebase.firestore.collection(COMMUNITY)
    private val storageRef = Firebase.storage.reference

    fun getArticle(key: String): MutableLiveData<Community> {
        val article: MutableLiveData<Community> = MutableLiveData()

        communityRef.document(key)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e(TAG, "Article listening failed", error)
                    article.value = null

                    return@addSnapshotListener
                }
                if (value != null) {
                    val item = value.toObject(Community::class.java)
                    article.value = item
                }
            }
        return article
    }

    fun getAllArticles(): LiveData<List<Community>> {
        val allArticles: MutableLiveData<List<Community>> = MutableLiveData()

        communityRef
            .orderBy("timeInMillis", Query.Direction.DESCENDING) // 등록 시간 역순으로 가져옴
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e(TAG, "Articles listening failed", error)
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

    fun getAllComments(key: String): LiveData<List<Comment>> {
        val allComments: MutableLiveData<List<Comment>> = MutableLiveData()

        communityRef.document(key)
            .collection(COMMENT)
            .orderBy(TIME_IN_MILLIS, Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e(TAG, "Comments listening failed", error)
                    allComments.value = null

                    return@addSnapshotListener
                }
                val list = mutableListOf<Comment>()
                if (value != null) {
                    for (doc in value) {
                        val item = doc.toObject(Comment::class.java)
                        list.add(item)
                    }
                    Log.d("COMMUNITY_VIEW_MODEL", list.toString())
                    allComments.value = list
                }
            }
        return allComments
    }

    fun getImageUri(key: String): MutableLiveData<Uri> {
        val uri: MutableLiveData<Uri> = MutableLiveData()

        storageRef.child("$key.jpg")
            .downloadUrl
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    uri.value = task.result
                } else {
                    uri.value = null
                }
            }
        return uri
    }

    fun getNewArticleKey(): String {
        // 자동 생성된 문서 id를 key로 사용하는 Community article을 만들기 위함
        return communityRef.document().id
    }

    fun getNewCommentKey(articleKey: String): String {
        return communityRef.document(articleKey)
            .collection(COMMENT)
            .document()
            .id
    }

    fun insertArticle(article: Community) {
        communityRef.document(article.key).set(article)
    }

    fun insertImage(key: String, data: ByteArray) {
        storageRef.child("$key.jpg").putBytes(data)
    }

    fun insertComment(articleKey: String, commentKey: String, comment: Comment) {
        communityRef.document(articleKey)
            .collection(COMMENT)
            .document(commentKey)
            .set(comment)
    }

    fun deleteArticle(article: Community) {
        communityRef.document(article.key).delete()
    }

    companion object {
        private const val TAG = "COMMUNITY_REPOSITORY"
    }
}