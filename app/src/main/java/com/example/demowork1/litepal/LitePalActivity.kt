package com.example.demowork1.litepal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.demowork1.R
import com.example.demowork1.litepal.model.DataEntity1
import com.example.demowork1.util.LogUtil
import org.litepal.LitePal
import java.lang.Exception


class LitePalActivity : AppCompatActivity() {

    private var testData1 = DataEntity1(1,"aaa")
    private var textShow:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lite_pal)
        var btnAdd = findViewById<Button>(R.id.btn_add)
        textShow = findViewById(R.id.text_db)
        var btnQuery: Button = findViewById(R.id.btn_query)
        var btnDelete: Button = findViewById(R.id.btn_delete)
        var btnUpdate: Button = findViewById(R.id.btn_update)
        var editText:EditText = findViewById(R.id.edit_text_data)
        var testData1 = DataEntity1(1,"aaa")
        btnAdd.setOnClickListener {
            addData("ccc")
        }
        btnQuery.setOnClickListener {
           queryData()
        }
        btnDelete.setOnClickListener {
//            删除数据表中指定ID的数据
            LitePal.delete(DataEntity1::class.java,1)
//            清空整个数据表
//            LitePal.deleteAll(DataEntity1::class.java)
//            条件删除
//            LitePal.deleteAll(DataEntity1::class.java, "id = ?", "1")
        }

        btnUpdate.setOnClickListener {
            try {
                testData1.name = "ddd"
//                testData1.update(1)
                testData1.updateAll("name = ? and id < ?", "ccc", "3")
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }

    }

    private fun addData(data:String){
        testData1.name = data
        Thread(Runnable {
            if (testData1.save()) {
                LogUtil.instance.d("添加数据成功")
            } else
                LogUtil.instance.d("添加数据失败")
        }).start()
    }
    private fun queryData(){
        var listData = LitePal.findAll(DataEntity1::class.java)
        var listData1 = LitePal.find(DataEntity1::class.java,1)
        var listData2 = LitePal.findAll(DataEntity1::class.java,1,2,3)
        var listData3 = LitePal.select("name")
            .where("id < ?","3")
            .order("id desc")
            .find(DataEntity1::class.java)

        var data = ""
        for (ll in listData) {
            data += ll.toString()
        }
        data = data+"    第二个查询结果："+listData1?.toString()+"    第三个查询结果："
        for(ll in listData2){
            data+=ll.toString()
        }
        data+="    第四个查询结果："
        for(ll in listData3){
            data+=ll.toString()
        }
        textShow?.text = data
        LogUtil.instance.d(data)
    }


}