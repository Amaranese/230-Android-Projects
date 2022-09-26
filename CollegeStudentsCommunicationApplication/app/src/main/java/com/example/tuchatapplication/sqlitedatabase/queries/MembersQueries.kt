package com.example.tuchatapplication.sqlitedatabase.queries

import com.example.tuchatapplication.sqlitedatabase.tables.MembersTable

class MembersQueries {
    companion object{
        var MEMBERS_SQL_ENTRIES = "CREATE TABLE ${MembersTable.TABLE_NAME} (" +
                "${MembersTable.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE," +
                "${MembersTable.COLUMN_NAME_GROUPID} TEXT," +
                "${MembersTable.COLUMN_NAME_CODE} TEXT," +
                "${MembersTable.COLUMN_NAME_DATE_ADDED} TEXT," +
                "${MembersTable.COLUMN_NAME_USERID} TEXT)"


        var MEMBERS_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${MembersTable.TABLE_NAME}"
    }
}