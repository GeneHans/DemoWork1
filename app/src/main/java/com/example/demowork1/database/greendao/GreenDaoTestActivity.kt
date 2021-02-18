package com.example.demowork1.database.greendao

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.demowork1.R
import com.example.demowork1.database.dbflow.DbFlowTestModel

class GreenDaoTestActivity : AppCompatActivity() {
    var btnAdd: Button? = null
    var btnQuery: Button? = null
    var btnDelete: Button? = null
    var btnUpdate: Button? = null
    var textShow: TextView? = null
    var editText: EditText? = null
    var addNum = 0
    var updateNum = 0
    var deleteNum = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_green_dao_test)
        btnAdd = findViewById(R.id.btn_add)
        btnQuery = findViewById(R.id.btn_query)
        btnDelete = findViewById(R.id.btn_delete)
        btnUpdate = findViewById(R.id.btn_update)
        textShow = findViewById(R.id.text_db)
        editText = findViewById(R.id.edit_text_data)
        setClickEvent()
    }

    private fun setClickEvent() {
        btnAdd?.setOnClickListener {
            var entity1 = DbFlowTestModel(addNum, "add data$addNum")
            entity1.save()
            addNum++
        }
        btnDelete?.setOnClickListener {
            deleteNum++
        }
        btnUpdate?.setOnClickListener {
            updateNum++
        }
        btnQuery?.setOnClickListener {
        }
    }
}