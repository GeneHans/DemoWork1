package com.example.demowork1.database.sqlite

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.demowork1.util.LogUtil

class DataBaseHelper(
    context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?,
    version: Int, errorHandler: DatabaseErrorHandler?
) : SQLiteOpenHelper(context, name, factory, version, errorHandler) {

    init {

    }

    override fun onCreate(db: SQLiteDatabase?) {
        var sqlCreate =
            "create table " + TEST_TABLE_NAME + "(" + TablePerson.ID_COLUMN + " integer primary key autoincrement," +
                    TablePerson.NAME_COLUMN + " varchar(64))"
        db?.execSQL(sqlCreate)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // 使用 SQL的ALTER语句
        LogUtil.instance.d("onUpgrade，newVersion is $newVersion")
        if (db == null)
            return
        db.beginTransaction()
        try {
            for (j in oldVersion..newVersion) {
                when (j) {
                    2 -> {
                        var sql =
                            "alter table $TEST_TABLE_NAME add other varchar(64)"
                        LogUtil.instance.d(sql)
                        db?.execSQL(sql)
                    }
                    3 -> {
                    }
                    else -> {
                    }
                }
            }
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.instance.d("Monicat:SQLiteDatabase upgrade failed.")
        } finally {
            db.endTransaction()
        }

    }

    companion object {
        const val TEST_TABLE_NAME = "person"
    }
}
