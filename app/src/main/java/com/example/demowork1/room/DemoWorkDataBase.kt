package com.example.demowork1.room

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper

@Database(entities = [TestListPageEntity::class], version = 1, exportSchema = false)
abstract class DemoWorkDataBase : RoomDatabase() {

    abstract fun getTestPageDataDao(): TestPageDataDao

    companion object {
        var instance: DemoWorkDataBase? = null
        const val RoomDataBaseName = "room_db"
        fun getInstance(context: Context): DemoWorkDataBase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, DemoWorkDataBase::class.java, RoomDataBaseName)
                        .build()
            }
            return instance!!
        }
    }
}