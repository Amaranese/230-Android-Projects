package com.example.timetablemanager.databaseHandler

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.timetablemanager.taskScheduler.Task

class DatabaseOperations(context: Context): SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "Tasks.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DatabaseInfo.SQL_CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DatabaseInfo.SQL_DELETE_TABLE_QUERY)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    // add, fetch, modify/update, delete

    fun addItem(dbo: DatabaseOperations, task: Task) {
        val db = dbo.writableDatabase
        val itemName = task.taskName
        val startTime= task.startTime
        val endTime= task.endTime
        val date=task.dateToDo
        val contentValues = ContentValues().apply {
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME, itemName)
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_START, startTime)
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_END,endTime )
            put(DatabaseInfo.TableInfo.COLUMN_DATE,date)
        }

        val rowID = db.insert(DatabaseInfo.TableInfo.TABLE_NAME, null, contentValues)
    }

    fun getItems(dbo: DatabaseOperations, date:String): Cursor {
        val db = dbo.readableDatabase
        val projection = arrayOf(
            BaseColumns._ID,
            DatabaseInfo.TableInfo.COLUMN_ITEM_NAME,
            DatabaseInfo.TableInfo.COLUMN_ITEM_START,
            DatabaseInfo.TableInfo.COLUMN_ITEM_END,
            DatabaseInfo.TableInfo.COLUMN_DATE)
        val selection = DatabaseInfo.TableInfo.COLUMN_DATE+ "=?"
        val selectionArgs = arrayOf(date)
        val sortOrder = null

        val cursor = db.query(
            DatabaseInfo.TableInfo.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
        return cursor
    }

    fun updateItem(dbo: DatabaseOperations, oldItem: Task, newItem: Task) {
        val db = dbo.writableDatabase
        val item = newItem.name
        val start =newItem.startTime
        val end=newItem.endTime
        val date=newItem.dateToDo

        val contentValues = ContentValues().apply {
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME, item)
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_START, start)
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_END, end)
            put(DatabaseInfo.TableInfo.COLUMN_DATE, date)
        }

        val selection = "${DatabaseInfo.TableInfo.COLUMN_ITEM_NAME} LIKE ? AND "+
                "${DatabaseInfo.TableInfo.COLUMN_ITEM_START} LIKE ? AND "+
                "${DatabaseInfo.TableInfo.COLUMN_ITEM_END} LIKE ? AND "+
                "${DatabaseInfo.TableInfo.COLUMN_DATE} LIKE ?"
        val selectionArgs = arrayOf(oldItem.name,oldItem.startTime,oldItem.endTime,oldItem.dateToDo)

        val count = db.update(DatabaseInfo.TableInfo.TABLE_NAME, contentValues, selection, selectionArgs)
    }

    fun deleteItem(dbo: DatabaseOperations, todoItem: Task) {
        val db = dbo.writableDatabase
        val selection = "${DatabaseInfo.TableInfo.COLUMN_ITEM_NAME} LIKE ? AND "+
                "${DatabaseInfo.TableInfo.COLUMN_ITEM_START} LIKE ? AND "+
                "${DatabaseInfo.TableInfo.COLUMN_ITEM_END} LIKE ? AND "+
                "${DatabaseInfo.TableInfo.COLUMN_DATE} LIKE ?"
        val selectionArgs = arrayOf(todoItem.name,todoItem.startTime,todoItem.endTime,todoItem.dateToDo)

        val deletedRows = db.delete(DatabaseInfo.TableInfo.TABLE_NAME, selection, selectionArgs)
    }
}