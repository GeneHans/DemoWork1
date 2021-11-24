package com.example.demowork1.database.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = TestListPageEntity.tableName)
class TestListPageEntity {

    @PrimaryKey
    var id: Long = 0

    @ColumnInfo
    var title: String = ""

    @ColumnInfo
    var content: String = ""

    constructor(id: Long, title: String, content: String) {
        this.id = id
        this.title = title
        this.content = content
    }

    override fun toString(): String {
        return "id=$id   title=$title   content=$content"
    }

    companion object {
        const val tableName = "test_page_list"
    }
}