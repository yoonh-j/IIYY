package com.yoond.iiyy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yoond.iiyy.utils.DATABASE_NAME
import java.util.concurrent.Executors

@Database(entities = [Supplement::class, State::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun supplementDao(): SupplementDao
    abstract fun stateDao(): StateDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // initial database
                            Executors.newSingleThreadExecutor().execute(
                                Runnable {
                                    instance?.supplementDao()?.insertSupplementList(
                                        listOf(
                                            Supplement("MSM", 1640707860000, false),
                                            Supplement("오메가3", 1640715300000,  true),
                                            Supplement("비타민B", 1640736000000,  false),
                                            Supplement("Alpha GPC", 1640742300000, true),
                                            Supplement("비타민C", 1640742900000, false),
                                            Supplement("종합비타민", 1640742900000, true),
                                            Supplement("프로틴", 1640775300000, true),
                                            Supplement("루테인", 1640772000000, false)
                                        )
                                    )
                                }
                            )
                        }
                    }
                ).build()
        }
    }
}