package com.example.sqljoins.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sqljoins.model.Company
import com.example.sqljoins.model.Department
import com.example.sqljoins.model.EmployeeDetails
import com.example.sqljoins.utils.Constants

class DatabaseHelper(private val context: Context) :
    SQLiteOpenHelper(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION) {

    override fun onCreate(database: SQLiteDatabase) {

        val CREATE_COMPANY_TABLE = "CREATE TABLE " + Constants.COMPANY_TABLE + " (" +
                Constants.ID + " INTEGER PRIMARY KEY," +
                Constants.NAME + " TEXT NOT NULL," +
                Constants.AGE + " INTEGER NOT NULL," +
                Constants.ADDRESS + " TEXT NOT NULL," +
                Constants.SALARY + " REAL)"

        val CREATE_DEPARTMENT_TABLE = "CREATE TABLE " + Constants.DEPARTMENT_TABLE + " (" +
                Constants.ID + " INTEGER PRIMARY KEY," +
                Constants.DEPT + " TEXT NOT NULL," +
                Constants.EMP_ID + " INTEGER NOT NULL)"

        database.execSQL(CREATE_COMPANY_TABLE)
        database.execSQL(CREATE_DEPARTMENT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object {

        private var databaseHelper: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper {
            if (databaseHelper == null) {
                databaseHelper = DatabaseHelper(context)
            }
            return databaseHelper!!
        }

    }

    fun insertCompanyData(company: Company) {
        val database = writableDatabase
        database.execSQL("INSERT INTO " + Constants.COMPANY_TABLE + "(" + "id,name,age,address,salary" + ")" + " VALUES " + " (" + company.id + ",'" + company.name + "','" + company.age + "','" + company.address + "','" + company.salary + "')")
    }


    fun insertDepartmentData(department: Department) {
        val database = writableDatabase
        database.execSQL("INSERT INTO " + Constants.DEPARTMENT_TABLE + "(" + "id,dept,empId" + ")" + " VALUES " + " (" + department.id + ",'" + department.dept + "','" + department.empId + "')")
    }

    fun getTotalCompanyEmployeeSize(): Int {
        val database = readableDatabase
        var size = 0
        val query = "SELECT count(*) FROM ${Constants.COMPANY_TABLE}"
        val cursor = database.rawQuery(query, null)
        if (cursor != null && cursor.moveToNext()) {
            size = cursor.getInt(0)
        }
        cursor.close()
        return size
    }

    fun getDepartmentSize(): Int {
        val database = readableDatabase
        var size: Int = 0
        val query = "SELECT count(*) FROM ${Constants.DEPARTMENT_TABLE}"
        val cursor = database.rawQuery(query, null)
        if (cursor != null && cursor.moveToNext()) {
            size = cursor.getInt(0)
        }
        cursor.close()
        return size
    }

    fun performInnerJoin(): List<EmployeeDetails> {
        val data = ArrayList<EmployeeDetails>()
        val database = readableDatabase
        val query =
            "SELECT Company.id, Company.name, Company.address, Dept.id, Dept.empId, Dept.dept FROM ${Constants.COMPANY_TABLE} Company INNER JOIN ${Constants.DEPARTMENT_TABLE} Dept on Company.id = Dept.id"

        val cursor = database.rawQuery(query, null)
        if (cursor != null) {
            while (cursor.moveToNext()){
                data.add(EmployeeDetails(
                    id = cursor.getInt(0),
                    name = cursor.getString(1),
                    age = 0,
                    address = cursor.getString(2),
                    salary = 0.0,
                    deptId = cursor.getInt(3),
                    empId = cursor.getInt(4),
                    dept = cursor.getString(5)
                ))
            }
        }
        cursor.close()
        return data
    }

    fun performCrossJoin() : List<EmployeeDetails>{
        val data = ArrayList<EmployeeDetails>()
        val database = readableDatabase
        val query =
            "SELECT Company.id, Company.name, Company.address, Dept.id, Dept.empId, Dept.dept FROM ${Constants.COMPANY_TABLE} Company CROSS JOIN ${Constants.DEPARTMENT_TABLE} Dept"

        val cursor = database.rawQuery(query, null)
        if (cursor != null) {
            while (cursor.moveToNext()){
                data.add(EmployeeDetails(
                    id = cursor.getInt(0),
                    name = cursor.getString(1),
                    age = 0,
                    address = cursor.getString(2),
                    salary = 0.0,
                    deptId = cursor.getInt(3),
                    empId = cursor.getInt(4),
                    dept = cursor.getString(5)
                ))
            }
        }
        cursor.close()
        return data
    }

    fun performLeftOuterJoin() : List<EmployeeDetails>{
        val data = ArrayList<EmployeeDetails>()
        val database = readableDatabase
        val query =
            "SELECT Company.id, Company.name, Company.address, Dept.id, Dept.empId, Dept.dept FROM ${Constants.COMPANY_TABLE} Company LEFT JOIN ${Constants.DEPARTMENT_TABLE} Dept on Company.id = Dept.id"

        val cursor = database.rawQuery(query, null)
        if (cursor != null) {
            while (cursor.moveToNext()){
                data.add(EmployeeDetails(
                    id = cursor.getInt(0),
                    name = cursor.getString(1),
                    age = 0,
                    address = cursor.getString(2),
                    salary = 0.0,
                    deptId = cursor.getInt(3),
                    empId = cursor.getInt(4),
                    dept = cursor.getString(5)
                ))
            }
        }
        cursor.close()
        return data
    }

    fun performRightOuterJoin() : List<EmployeeDetails>{
        val data = ArrayList<EmployeeDetails>()
        val database = readableDatabase
        val query =
            "SELECT Company.id, Company.name, Company.address, Dept.id, Dept.empId, Dept.dept FROM ${Constants.COMPANY_TABLE} Company LEFT JOIN ${Constants.DEPARTMENT_TABLE} Dept on Company.id = Dept.id"

        val query2 =
            "SELECT Company.id, Company.name, Company.address, Dept.id, Dept.empId, Dept.dept FROM ${Constants.COMPANY_TABLE} Company LEFT JOIN ${Constants.DEPARTMENT_TABLE} Dept on Company.id = Dept.id"

        val cursor = database.rawQuery("$query union $query2", null)
        if (cursor != null) {
            while (cursor.moveToNext()){
                data.add(EmployeeDetails(
                    id = cursor.getInt(0),
                    name = cursor.getString(1),
                    age = 0,
                    address = cursor.getString(2),
                    salary = 0.0,
                    deptId = cursor.getInt(3),
                    empId = cursor.getInt(4),
                    dept = cursor.getString(5)
                ))
            }
        }
        cursor.close()
        return data
    }
}