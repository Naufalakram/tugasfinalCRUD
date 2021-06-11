package id.ac.unhas.tugascrud

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception
import java.util.ArrayList

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAMA, null, DATABASE_VERSION) {

    companion object{

        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAMA = "data.db"
        private const val TBL_DATA = "tbl_data"
        private const val ID = "id"
        private const val USERNAME = "nama"
        private const val EMAIL = "email"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblData = ("CREATE TABLE " + TBL_DATA +"("
                + ID + " INTEGER PRIMARY KEY, " +  USERNAME + " TEXT,"
                + EMAIL + " TEXT" + ")")
        db?.execSQL(createTblData)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_DATA")
        onCreate(db)
    }

    fun insertData(data: ModelCRUD): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, data.id)
        contentValues.put(USERNAME, data.username)
        contentValues.put(EMAIL, data.email)

        val success = db.insert(TBL_DATA, null, contentValues)
        db.close()
        return success

    }

    fun getAllData(): ArrayList<ModelCRUD> {
        val dataList: ArrayList<ModelCRUD> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_DATA"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var username: String
        var email: String

        if (cursor.moveToFirst()) {
            do {

                id = cursor.getInt(cursor.getColumnIndex("id"))
                username = cursor.getString(cursor.getColumnIndex("nama"))
                email = cursor.getString(cursor.getColumnIndex("email"))

                val data = ModelCRUD(id = id, username = username, email = email)
                dataList.add(data)

            } while (cursor.moveToNext())
        }
        return dataList
    }

    fun updateData(data: ModelCRUD): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, data.id)
        contentValues.put(USERNAME, data.username)
        contentValues.put(EMAIL, data.email)

        val success = db.update(TBL_DATA, contentValues, "id= " + data.id, null)
        db.close()
        return success
    }

    fun deleteDataById(id: Int): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(TBL_DATA, "id= $id", null)
        db.close()
        return success
    }
}