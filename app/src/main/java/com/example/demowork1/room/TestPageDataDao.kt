package com.example.demowork1.room

import androidx.paging.DataSource
import androidx.room.*

@Dao
abstract class TestPageDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertTestPageData(data: TestListPageEntity)

    @Query("DELETE FROM " + TestListPageEntity.tableName)
    abstract fun deleteAllData()

    @Query("SELECT * FROM " + TestListPageEntity.tableName + " ORDER BY id")
    abstract fun queryAll(): DataSource.Factory<Integer, TestListPageEntity>?

    @Query("SELECT * FROM " + TestListPageEntity.tableName + " ORDER BY id")
    abstract suspend fun queryAllData(): List<TestListPageEntity>?

    @Query("SELECT * FROM " + TestListPageEntity.tableName + " where id = (:id)")
    abstract fun queryById(id: Long): TestListPageEntity?

    @Query("delete from " + TestListPageEntity.tableName + " where id =(:id)")
    abstract fun deleteById(id: Long)

    @Transaction
    open fun deleteDataById(userId: Long) {
        if (queryById(userId) != null) {
            deleteById(userId)
        }
    }
}