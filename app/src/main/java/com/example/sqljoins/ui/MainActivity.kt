package com.example.sqljoins.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.sqljoins.databinding.ActivityMainBinding
import com.example.sqljoins.db.DatabaseHelper
import com.example.sqljoins.model.Company
import com.example.sqljoins.model.Department
import com.example.sqljoins.model.EmployeeDetails

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: DatabaseHelper
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper.getInstance(this)
        /*insertCompanyData()
        insertDepartData()*/

        binding.btStart.setOnClickListener {
            val data: List<EmployeeDetails> = databaseHelper.performLeftOuterJoin()
            Log.i(TAG, data.size.toString())
            for (values in data)
                Log.i(TAG, values.toString())
        }


    }

    private fun insertCompanyData() {
        databaseHelper.insertCompanyData(Company(1, "Paul", 32, "California", 20000.0))
        databaseHelper.insertCompanyData(Company(2, "Allen", 25, "Texas", 15000.0))
        databaseHelper.insertCompanyData(Company(3, "Teddy", 23, "Norway", 20000.0))
        databaseHelper.insertCompanyData(Company(4, "Mark", 25, "Rich-Mond", 65000.0))
        databaseHelper.insertCompanyData(Company(5, "David", 27, "Texas", 85000.0))
        databaseHelper.insertCompanyData(Company(6, "Kim", 22, "South-Hall ", 45000.0))
        databaseHelper.insertCompanyData(Company(7, "James", 24, "Houston", 10000.0))
    }

    private fun insertDepartData() {
        databaseHelper.insertDepartmentData(Department(1, "IT Billing", 1))
        databaseHelper.insertDepartmentData(Department(2, "Engineering", 2))
        databaseHelper.insertDepartmentData(Department(3, "Engineering", 7))
    }


}