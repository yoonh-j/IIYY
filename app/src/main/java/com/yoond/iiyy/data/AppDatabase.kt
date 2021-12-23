package com.yoond.iiyy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yoond.iiyy.utils.DATABASE_NAME
import java.util.concurrent.Executors

@Database(entities = [Supplement::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun supplementDao(): SupplementDao

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
                            Executors.newSingleThreadExecutor().execute(
                                Runnable {
                                    instance?.supplementDao()?.insertSupplementList(
                                        listOf(
                                            Supplement("1", "MSM", 1640081700000, 7, false),
                                            Supplement("2", "오메가3", 1639537800000, 3, true),
                                            Supplement("3", "비타민B", 1636956600000, 1, false),
                                            Supplement("4", "Alpha GPC", 1605412080000, 1, true),
                                            Supplement("5", "비타민C", 1640081700000, 4, false),
                                            Supplement("6", "종합비타민", 1640185200000, 4, true),
                                            Supplement("7", "프로틴", 1640271599000, 1, true),
                                            Supplement("8", "루테인", 1640271600000, 1, false)
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