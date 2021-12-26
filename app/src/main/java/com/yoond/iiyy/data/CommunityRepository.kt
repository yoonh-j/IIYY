package com.yoond.iiyy.data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yoond.iiyy.utils.COMMUNITY
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommunityRepository @Inject constructor() {
    private val communityRef = Firebase.firestore.collection(COMMUNITY)

    fun getAllArticles(): CollectionReference = communityRef

    fun getNewKey(): String {
        // 자동 생성된 문서 id를 key로 사용하는 Community article을 만들기 위함
        return communityRef.document().id
    }

    fun insertArticle(article: Community) {
        communityRef.document(article.key).set(article)
    }

    fun deleteArticle(article: Community) {
        communityRef.document(article.key).delete()
    }
}