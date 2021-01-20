package com.example.demowork1.database.dbflow

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(name = DbFlowTestModel.TABLE_NAME, database = DbFlowDatabase::class)
class DbFlowTestModel : BaseModel {

    @PrimaryKey
    var id: Int = 0

    @Column
    var name: String? = null

    constructor(id: Int, name: String?) {
        this.id = id
        this.name = name
    }

    companion object {
        const val TABLE_NAME = "test_db_flow"
    }
}