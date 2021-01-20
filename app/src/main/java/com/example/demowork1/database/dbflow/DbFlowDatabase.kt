package com.example.demowork1.database.dbflow

import com.raizlabs.android.dbflow.annotation.Database

@Database(name = DbFlowDatabase.DB_FLOW_NAME, version = DbFlowDatabase.DB_FLOW_VERSION)
class DbFlowDatabase {

    companion object {
        const val DB_FLOW_VERSION = 1
        const val DB_FLOW_NAME = "dbflow_database"
    }
}