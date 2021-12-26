package com.yoond.iiyy.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yoond.iiyy.utils.COMMENT
import com.yoond.iiyy.utils.COMMUNITY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class FirebaseModule {

    @Singleton
    @Provides
    fun provideFirestore() = Firebase.firestore

    @Singleton
    @Provides
    fun provideStorage() = Firebase.storage

    @Singleton
    @Provides
    fun provideCommunityRef(fireStore: FirebaseFirestore) = fireStore.collection(COMMUNITY)

    @Singleton
    @Provides
    fun provideCommentRef(fireStore: FirebaseFirestore) = fireStore.collection(COMMENT)
}