package com.alejandro.maranes.tuchatapplication.sqlitedatabase.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.alejandro.maranes.tuchatapplication.models.User
import com.alejandro.maranes.tuchatapplication.sqlitedatabase.database.Database
import com.alejandro.maranes.tuchatapplication.sqlitedatabase.queries.ChatEntries
import com.alejandro.maranes.tuchatapplication.sqlitedatabase.queries.GroupQueries
import com.alejandro.maranes.tuchatapplication.sqlitedatabase.queries.MembersQueries
import com.alejandro.maranes.tuchatapplication.sqlitedatabase.queries.TableQueries
import com.alejandro.maranes.tuchatapplication.sqlitedatabase.tables.UserTable

class UsersSqliteDatabaseHelper(context: Context): SQLiteOpenHelper(context, Database.DATABASE_NAME, null, Database.DATABASE_VERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL(TableQueries.SQL_CREATE_ENTRIES)
        p0.execSQL(GroupQueries.GROUP_SQL_ENTRIES)
        p0.execSQL(MembersQueries.MEMBERS_SQL_ENTRIES)
        p0.execSQL(ChatEntries.CHAT_SQL_ENTRIES)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL(TableQueries.SQL_DELETE_ENTRIES)
        p0.execSQL(GroupQueries.GROUP_DELETE_SQL)
        p0.execSQL(MembersQueries.MEMBERS_DELETE_ENTRIES)
        p0.execSQL(ChatEntries.CHAT_SQL_DROP)
        onCreate(p0)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

}