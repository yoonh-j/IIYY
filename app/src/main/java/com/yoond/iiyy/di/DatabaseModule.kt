package com.yoond.iiyy.di

import android.content.Context
import com.yoond.iiyy.data.AppDatabase
import com.yoond.iiyy.data.SupplementDao
import com.yoond.iiyy.data.SupplementRepository
import com.yoond.iiyy.viewmodels.SupplementListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideSupplementDao(appDatabase: AppDatabase): SupplementDao {
        return appDatabase.supplementDao()
    }
}