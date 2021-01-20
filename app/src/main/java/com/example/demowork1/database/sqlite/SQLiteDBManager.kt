package com.example.demowork1.database.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.demowork1.util.LogUtil

class SQLiteDBManager(var context: Context) {

    init {
    }

    /**
     * 创建数据库对象
     */
    fun createDb(version: Int) {
        var sqLiteHelper = DataBaseHelper(
            context,
            "sqlite_test",
            null,
            version,
            null
        )
        sqLiteDB = sqLiteHelper.readableDatabase
        //和read方法一样都能够获得一个可读写的数据库对象，注意和read的区别
//        sqLiteDB = sqLiteHelper.writableDatabase
    }

    /**
     * 使用insert方法添加数据
     * @param:插入的数据
     */
    fun insert1(person: TablePerson) {
        sqLiteDB?.beginTransaction() ?: return
        try {
            if (checkExistData(person.id)) {
                LogUtil.instance.toast("数据已经存在", context)
            } else {
                var values = ContentValues()
                values.put(TablePerson.ID_COLUMN, person.id)
                values.put(TablePerson.NAME_COLUMN, person.name)
                sqLiteDB?.insert(
                    DataBaseHelper.TEST_TABLE_NAME, null, values)
            }
            sqLiteDB?.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.instance.d("插入数据失败")
        } finally {
            sqLiteDB?.endTransaction()
        }
    }

    /**
     * 使用SQL语句添加数据
     * @param person:插入的数据
     * 注意需要在字符串添加''
     */
    fun insert2(person: TablePerson) {
        sqLiteDB?.beginTransaction() ?: return
        try {
            if (checkExistData(person.id)) {
                LogUtil.instance.toast("数据已经存在", context)
            } else {
                var insertSql =
                    "insert into " + DataBaseHelper.TEST_TABLE_NAME + " (" + TablePerson.ID_COLUMN + " , " +
                            TablePerson.NAME_COLUMN + ") values (" + person.id + " , '" + person.name + "')"
                sqLiteDB?.execSQL(insertSql)
            }
            sqLiteDB?.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.instance.d("插入数据失败")
        } finally {
            sqLiteDB?.endTransaction()
        }
    }

    /**
     * 使用update方法更新数据
     * @param:更新的数据
     */
    fun update1(person: TablePerson) {
        sqLiteDB?.beginTransaction() ?: return
        try {
            if (checkExistData(person.id)) {

                // a. 创建一个ContentValues对象
                var values = ContentValues()
                values.put(TablePerson.NAME_COLUMN, person.name)
                // b. 调用update方法修改数据库：将id=1 修改成 name = zhangsan
                sqLiteDB?.update(
                    DataBaseHelper.TEST_TABLE_NAME,
                    values,
                    TablePerson.ID_COLUMN + "=?",
                    arrayOf(person.id.toString())
                )
                // 参数1：表名(String)
                // 参数2：需修改的ContentValues对象
                // 参数3：WHERE表达式（String），需数据更新的行； 若该参数为 null, 就会修改所有行；？号是占位符
                // 参数4：WHERE选择语句的参数(String[]), 逐个替换 WHERE表达式中 的“？”占位符;

            } else {
                LogUtil.instance.toast("数据不存在", context)
            }
            sqLiteDB?.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.instance.d("更新数据失败")
        } finally {
            sqLiteDB?.endTransaction()
        }
    }

    /**
     * 使用SQL语句更新数据
     * @param:更新的数据。
     * 注意字符串添加''
     */
    fun update2(person: TablePerson) {
        sqLiteDB?.beginTransaction() ?: return
        try {
            if (checkExistData(person.id)) {
                // 注：也可采用SQL语句修改
                var updateSql =
                    "update " + DataBaseHelper.TEST_TABLE_NAME + " set " + TablePerson.NAME_COLUMN +
                            " = '" + person.name + "' where " + TablePerson.ID_COLUMN + " = " + person.id
                sqLiteDB?.execSQL(updateSql)
            } else {
                LogUtil.instance.toast("数据不存在", context)
            }
            sqLiteDB?.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.instance.d("更新数据失败")
        } finally {
            sqLiteDB?.endTransaction()
        }
    }

    /**
     * 使用delete方法删除数据
     * @param:删除数据的ID
     */
    fun delete1(id: Int) {
        sqLiteDB?.beginTransaction() ?: return
        try {
            if (checkExistData(id)) {
                sqLiteDB?.delete(
                    DataBaseHelper.TEST_TABLE_NAME, TablePerson.ID_COLUMN + "=?",
                    arrayOf(id.toString())
                )
            } else {
                LogUtil.instance.toast("数据不存在", context)
            }
            sqLiteDB?.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.instance.d("更新数据失败")
        } finally {
            sqLiteDB?.endTransaction()
        }
    }

    /**
     * 使用SQL语句删除数据
     * @param:删除数据的ID
     */
    fun delete2(id: Int) {
        sqLiteDB?.beginTransaction() ?: return
        try {
            if (checkExistData(id)) {
                // 注：也可采用SQL语句修改
                var deleteSql =
                    "delete from " + DataBaseHelper.TEST_TABLE_NAME + " where " +
                            TablePerson.ID_COLUMN + " = " + id
                sqLiteDB?.execSQL(deleteSql)
            } else {
                LogUtil.instance.toast("数据不存在", context)
            }
            sqLiteDB?.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.instance.d("更新数据失败")
        } finally {
            sqLiteDB?.endTransaction()
        }
    }

    /**
     * 判断数据库中是否存在数据
     */
    fun checkExistData(id: Int): Boolean {
        val result =
            sqLiteDB?.rawQuery("select * from person where id>?", arrayOf(id.toString()))
                ?: return false
        result.moveToFirst()
        var isExist = false
        while (!result.isAfterLast) {
            var mId: Int = result.getInt(0)
            var mName: String = result.getString(1)
            if (mId == id) {
                isExist = true
                break
            }
            result.moveToNext()
        }
        result.close()
        return isExist
    }

    /**
     * 测试的查询方法，使用了rawQuery方法，可以根据需求自定义SQL
     */
    fun queryTest1(id: Int) {
        val result =
            sqLiteDB?.rawQuery("select * from person where id>?", arrayOf(id.toString())) ?: return
        result.moveToFirst()
        while (!result.isAfterLast) {
            var mId: Int = result.getInt(0)
            var mName: String = result.getString(1)
            LogUtil.instance.d("id=$mId   name$mName")
            // do something useful with these
            result.moveToNext()
        }
        result.close()
    }

    /**
     * 测试的查询方法2 ，使用了query方法，可以根据需求自定义SQL
     */
    fun queryTest2(id: Int) {
        var result = sqLiteDB?.query(
            DataBaseHelper.TEST_TABLE_NAME,
            arrayOf(
                TablePerson.ID_COLUMN,
                TablePerson.NAME_COLUMN
            ), "id>?",
            arrayOf(id.toString()), null, null, null
        ) ?: return
        result.moveToFirst()
        while (!result.isAfterLast) {
            var mId: Int = result.getInt(0)
            var mName: String = result.getString(1)
            LogUtil.instance.d("id=$mId   name$mName")
            // do something useful with these
            result.moveToNext()
        }
        result.close()
    }

    /**
    //Cursor对象常用方法如下：
    c.move(int offset); //以当前位置为参考,移动到指定行
    c.moveToFirst();    //移动到第一行
    c.moveToLast();     //移动到最后一行
    c.moveToPosition(int position); //移动到指定行
    c.moveToPrevious(); //移动到前一行
    c.moveToNext();     //移动到下一行
    c.isFirst();        //是否指向第一条
    c.isLast();     //是否指向最后一条
    c.isBeforeFirst();  //是否指向第一条之前
    c.isAfterLast();    //是否指向最后一条之后
    c.isNull(int columnIndex);  //指定列是否为空(列基数为0)
    c.isClosed();       //游标是否已关闭
    c.getCount();       //总数据项数
    c.getPosition();    //返回当前游标所指向的行数
    c.getColumnIndex(String columnName);//返回某列名对应的列索引值
    c.getString(int columnIndex);   //返回当前行指定列的值

    // 方法说明
    db.query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy);
    db.query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit);
    db.query(String distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit);

    // 参数说明
    // table：要操作的表
    // columns：查询的列所有名称集
    // selection：WHERE之后的条件语句，可以使用占位符
    // groupBy：指定分组的列名
    // having指定分组条件，配合groupBy使用
    // orderBy指定排序的列名
    // limit指定分页参数
    // distinct可以指定“true”或“false”表示要不要过滤重复值

     */

    companion object {
        var sqLiteDB: SQLiteDatabase? = null
        fun getInstance(context: Context): SQLiteDBManager {
            return SQLiteDBManager(context)
        }
    }
}