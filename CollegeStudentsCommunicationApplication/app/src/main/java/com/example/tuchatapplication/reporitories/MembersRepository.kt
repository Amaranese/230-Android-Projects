package com.example.tuchatapplication.reporitories

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.tuchatapplication.models.Member
import com.example.tuchatapplication.sqlitedatabase.helpers.UsersSqliteDatabaseHelper
import com.example.tuchatapplication.sqlitedatabase.queries.MembersQueries
import com.example.tuchatapplication.sqlitedatabase.tables.MembersTable

class MembersRepository(context: Context) {
    private val TAG = "MembersRepository"
    private var usersSqliteDatabaseHelper: UsersSqliteDatabaseHelper? = null

    init {
        usersSqliteDatabaseHelper = UsersSqliteDatabaseHelper(context)
    }

    companion object{
        private var INSTANCE: MembersRepository? = null

        fun setInstance(context: Context): MembersRepository{
            if (INSTANCE == null){
                INSTANCE = MembersRepository(context)
            }

            return INSTANCE!!
        }
    }

    //MEMBERS
    //add member
    suspend fun addMember(member: Member): Long{
        var db: SQLiteDatabase = usersSqliteDatabaseHelper!!.writableDatabase
        var contentValues: ContentValues = ContentValues().apply {
            put(MembersTable.COLUMN_NAME_USERID, member.userId)
            put(MembersTable.COLUMN_NAME_GROUPID, member.groupId)
            put(MembersTable.COLUMN_NAME_CODE, member.code)
            put(MembersTable.COLUMN_NAME_DATE_ADDED, member.dateAdded)
        }

        var response = db.insert(MembersTable.TABLE_NAME, null, contentValues)

        Log.i(TAG, "addMember: ${response}")

        if (response >= 0){
            return response
        }
        else{
            return -1
        }

    }

    suspend fun getMembersByGroup(groupId: String): ArrayList<Member>{
        var db = usersSqliteDatabaseHelper!!.readableDatabase
        var projection =  arrayOf(
            MembersTable.COLUMN_NAME_ID, MembersTable.COLUMN_NAME_DATE_ADDED, MembersTable.COLUMN_NAME_USERID,
            MembersTable.COLUMN_NAME_GROUPID, MembersTable.COLUMN_NAME_CODE)
        var selection = "${MembersTable.COLUMN_NAME_GROUPID} = ?"
        var selectionArgs = arrayOf(groupId)

        var cursor = db.query(
            MembersTable.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var lst: ArrayList<Member> = ArrayList()

        while (cursor.moveToNext()){
            var member: Member = Member()
            member.userId = cursor.getString(cursor.getColumnIndexOrThrow(MembersTable.COLUMN_NAME_USERID))
            member.groupId = cursor.getString(cursor.getColumnIndexOrThrow(MembersTable.COLUMN_NAME_GROUPID))
            member.code = cursor.getString(cursor.getColumnIndexOrThrow(MembersTable.COLUMN_NAME_CODE))
            member.dateAdded = cursor.getString(cursor.getColumnIndexOrThrow(MembersTable.COLUMN_NAME_DATE_ADDED))

            lst.add(member)
        }

        return lst
    }

    suspend fun getMemberGroups(userId: String): ArrayList<String>{
        var db = usersSqliteDatabaseHelper!!.readableDatabase
        var projection =  arrayOf(
            MembersTable.COLUMN_NAME_ID, MembersTable.COLUMN_NAME_DATE_ADDED, MembersTable.COLUMN_NAME_USERID,
            MembersTable.COLUMN_NAME_GROUPID, MembersTable.COLUMN_NAME_CODE)
        var selection = "${MembersTable.COLUMN_NAME_USERID} = ?"
        var selectionArgs = arrayOf(userId)

        var cursor = db.query(
            MembersTable.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var lst: ArrayList<String> = ArrayList()

        while (cursor.moveToNext()){
            lst.add(cursor.getString(cursor.getColumnIndexOrThrow(MembersTable.COLUMN_NAME_GROUPID)))
        }

        return lst
    }

    suspend fun leaveGroup(userId: String, groupId: String): Int{
        var db = usersSqliteDatabaseHelper!!.writableDatabase
        var selection = "${MembersTable.COLUMN_NAME_USERID} = ? AND ${MembersTable.COLUMN_NAME_GROUPID} = ?"
        var selectionArgs = arrayOf(userId, groupId)
        var res = db.delete(MembersTable.TABLE_NAME,
            selection,
            selectionArgs
        )

        if (res >= 0){
            return res
        }
        return 0
    }

}