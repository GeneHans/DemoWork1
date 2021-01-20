package com.example.demowork1.database.sqlite

class TablePerson(
    var id: Int,
    var name: String
) {
    companion object {
        const val ID_COLUMN = "id"
        const val NAME_COLUMN = "name"
        const val OTHER_COLUMN = "other"
    }
}