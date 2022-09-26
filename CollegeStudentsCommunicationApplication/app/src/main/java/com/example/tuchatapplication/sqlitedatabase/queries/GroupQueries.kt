package com.example.tuchatapplication.sqlitedatabase.queries

import com.example.tuchatapplication.sqlitedatabase.tables.GroupTable

class GroupQueries {
    companion object{
        var GROUP_SQL_ENTRIES = "CREATE TABLE ${GroupTable.TABLE_NAME} (" +
                "${GroupTable.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE," +
                "${GroupTable.COLUMN_NAME_GROUP_ID} TEXT," +
                "${GroupTable.COLUMN_NAME_GROUP_NAME} TEXT," +
                "${GroupTable.COLUMN_NAME_DESCRIPTION} TEXT," +
                "${GroupTable.COLUMN_NAME_CAPACITY} TEXT," +
                "${GroupTable.COLUMN_NAME_IMAGE} TEXT," +
                "${GroupTable.COLUMN_NAME_DATE_CREATED} TEXT," +
                "${GroupTable.COLUMN_NAME_CREATEDBY} TEXT)"

        var GROUP_DELETE_SQL = "DROP TABLE IF EXISTS ${GroupTable.TABLE_NAME}"
    }
}