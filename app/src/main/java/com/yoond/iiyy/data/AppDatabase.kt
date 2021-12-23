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
                            Executors.newSingleThreadExecutor().execute(
                                Runnable {
                                    instance?.supplementDao()?.insertSupplementList(
                                        listOf(
                                            Supplement("1", "MSM", 1640081700000, false),
                                            Supplement("2", "오메가3", 1639537800000,  true),
                                            Supplement("3", "비타민B", 1636956600000,  false),
                                            Supplement("4", "Alpha GPC", 1605412080000, true),
                                            Supplement("5", "비타민C", 1640081700000, false),
                                            Supplement("6", "종합비타민", 1640185200000, true),
                                            Supplement("7", "프로틴", 1640271599000, true),
                                            Supplement("8", "루테인", 1640271600000, false)
                                        )
                                    )
                                    instance?.stateDao()?.insertStateList(
                                        listOf(
                                            State(1635692400000, 1),
                                            State(1636902000000, 0),
                                            State(1636988400000, 2),
                                            State(1638284400000, 0),
                                            State(1638370800000, 1),
                                            State(1639494000000, 2),
                                            State(1639580400000, 1),
                                            State(1639666800000, 1),
                                            State(1639926000000, 0),
                                            State(1640185200000, 1)
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