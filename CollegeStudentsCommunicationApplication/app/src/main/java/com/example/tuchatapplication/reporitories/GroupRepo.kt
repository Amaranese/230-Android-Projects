package com.example.tuchatapplication.reporitories

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.tuchatapplication.models.Group
import com.example.tuchatapplication.sqlitedatabase.helpers.UsersSqliteDatabaseHelper
import com.example.tuchatapplication.sqlitedatabase.tables.GroupTable

class GroupRepo(context: Context) {
    private val TAG = "GroupRepo"
    private var usersSqliteDatabaseHelper: UsersSqliteDatabaseHelper? = null

    init {
        usersSqliteDatabaseHelper = UsersSqliteDatabaseHelper(context)
    }

    companion object{
        private var INSTANCE: GroupRepo? = null

        fun setInstance(context: Context): GroupRepo{
            if (INSTANCE == null){
                INSTANCE = GroupRepo(context)
            }

            return INSTANCE!!
        }
    }

    //GROUPS
    //create group
    suspend fun createGroup(group: Group): Long{
        if (group.group_id != null){
            var db: SQLiteDatabase = usersSqliteDatabaseHelper!!.writableDatabase
            var contentValues: ContentValues = ContentValues().apply {
                put(GroupTable.COLUMN_NAME_GROUP_ID, group.group_id)
                put(GroupTable.COLUMN_NAME_GROUP_NAME, group.group_name)
                put(GroupTable.COLUMN_NAME_DESCRIPTION, group.group_description)
                put(GroupTable.COLUMN_NAME_CAPACITY, group.group_capacity)
                put(GroupTable.COLUMN_NAME_IMAGE, group.group_image)
                put(GroupTable.COLUMN_NAME_CREATEDBY, group.group_created_by)
                put(GroupTable.COLUMN_NAME_DATE_CREATED, group.group_date_created)
            }

            var response = db.insert(GroupTable.TABLE_NAME, null, contentValues)

            if (response >= 0){
                return response
            }
            else{
                return -1
            }
        }
        else{
            return -1
        }

    }

    suspend fun generateGroups(): ArrayList<Group>{
        var db: SQLiteDatabase = usersSqliteDatabaseHelper!!.readableDatabase
        var projection = arrayOf(
            GroupTable.COLUMN_NAME_ID, GroupTable.COLUMN_NAME_DATE_CREATED,
            GroupTable.COLUMN_NAME_CREATEDBY, GroupTable.COLUMN_NAME_IMAGE,
            GroupTable.COLUMN_NAME_CAPACITY, GroupTable.COLUMN_NAME_DESCRIPTION, GroupTable.COLUMN_NAME_GROUP_NAME, GroupTable.COLUMN_NAME_GROUP_ID)

        var cursor = db.query(
            GroupTable.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        var lst: ArrayList<Group> = ArrayList()

        while (cursor.moveToNext()){
            var group: Group = Group()
            group.group_id = cursor.getString(cursor.getColumnIndexOrThrow(GroupTable.COLUMN_NAME_GROUP_ID))
            group.group_name = cursor.getString(cursor.getColumnIndexOrThrow(GroupTable.COLUMN_NAME_GROUP_NAME))
            group.group_description = cursor.getString(cursor.getColumnIndexOrThrow(GroupTable.COLUMN_NAME_DESCRIPTION))
            group.group_image = cursor.getString(cursor.getColumnIndexOrThrow(GroupTable.COLUMN_NAME_IMAGE))
            group.group_capacity = cursor.getInt(cursor.getColumnIndexOrThrow(GroupTable.COLUMN_NAME_CAPACITY))
            group.group_date_created = cursor.getString(cursor.getColumnIndexOrThrow(GroupTable.COLUMN_NAME_DATE_CREATED))
            group.group_created_by = cursor.getString(cursor.getColumnIndexOrThrow(GroupTable.COLUMN_NAME_CREATEDBY))

            lst.add(group)
        }

        Log.i(TAG, "generateGroups: $lst")

        return lst
    }

    suspend fun getGroupDetails(id: String): Group {
        var db = usersSqliteDatabaseHelper!!.readableDatabase
        var projection = arrayOf(
            GroupTable.COLUMN_NAME_ID, GroupTable.COLUMN_NAME_DATE_CREATED,
            GroupTable.COLUMN_NAME_CREATEDBY, GroupTable.COLUMN_NAME_IMAGE,
            GroupTable.COLUMN_NAME_CAPACITY, GroupTable.COLUMN_NAME_DESCRIPTION, GroupTable.COLUMN_NAME_GROUP_NAME, GroupTable.COLUMN_NAME_GROUP_ID)

        var selection = "${GroupTable.COLUMN_NAME_GROUP_ID} = ?"
        var selectionArgs = arrayOf(id)

        var cursor = db.query(
            GroupTable.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var group: Group = Group()

        while (cursor.moveToNext()){
            group.group_id = cursor.getString(cursor.getColumnIndexOrThrow(GroupTable.COLUMN_NAME_GROUP_ID))
            group.group_name = cursor.getString(cursor.getColumnIndexOrThrow(GroupTable.COLUMN_NAME_GROUP_NAME))
            group.group_description = cursor.getString(cursor.getColumnIndexOrThrow(GroupTable.COLUMN_NAME_DESCRIPTION))
            group.group_image = cursor.getString(cursor.getColumnIndexOrThrow(GroupTable.COLUMN_NAME_IMAGE))
            group.group_capacity = cursor.getInt(cursor.getColumnIndexOrThrow(GroupTable.COLUMN_NAME_CAPACITY))
            group.group_date_created = cursor.getString(cursor.getColumnIndexOrThrow(GroupTable.COLUMN_NAME_DATE_CREATED))
            group.group_created_by = cursor.getString(cursor.getColumnIndexOrThrow(GroupTable.COLUMN_NAME_CREATEDBY))

        }

        return group
    }
}