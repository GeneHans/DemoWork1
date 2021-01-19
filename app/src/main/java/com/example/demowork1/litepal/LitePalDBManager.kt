package com.example.demowork1.litepal

import android.database.sqlite.SQLiteDatabase
import com.example.demowork1.litepal.model.DataEntity1
import com.example.demowork1.litepal.model.DataEntity2
import com.example.demowork1.litepal.model.DataEntity3
import com.example.demowork1.util.LogUtil
import org.litepal.LitePal
import org.litepal.LitePal.use
import org.litepal.LitePalDB

class LitePalDBManager {
    var litePalDB: LitePalDB? = null
    var demo1DB: SQLiteDatabase? = null
    fun initDB() {
        initMultiDB("testDataBase1")
        //获取到SQLiteDataBase实例，用来创建数据库
        demo1DB = LitePal.getDatabase()
        LogUtil.instance.d(demo1DB?.path ?:"no database path")
    }

    /**
     * 动态建立数据库，不去使用xml文件配置
     */
    private fun initMultiDB(dbName: String) {
        litePalDB = LitePalDB(dbName, 2)
        litePalDB?.addClassName(DataEntity1::class.java.name)
        litePalDB?.addClassName(DataEntity2::class.java.name)
        litePalDB?.addClassName(DataEntity3::class.java.name)
        if (litePalDB != null) {
            use(litePalDB!!)
        }
    }

    companion object {
        val instance: LitePalDBManager by lazy { LitePalDBManager() }
    }
}