package com.example.demowork1.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.demowork1.R

class SQLiteTestActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_s_q_lite_test)
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
            var person = TablePerson(addNum, "test$addNum")
            SQLiteDBManager.getInstance(this).insert2(person)
            addNum++
        }
        btnDelete?.setOnClickListener {
            SQLiteDBManager.getInstance(this).delete2(deleteNum)
            deleteNum++
        }
        btnUpdate?.setOnClickListener {
            var person = TablePerson(updateNum, "update$updateNum")
            SQLiteDBManager.getInstance(this).update2(person)
            updateNum++
        }
        btnQuery?.setOnClickListener {
            SQLiteDBManager.getInstance(this).queryTest2(1)
        }
    }
}