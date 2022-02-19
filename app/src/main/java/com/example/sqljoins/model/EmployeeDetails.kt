package com.example.sqljoins.model

data class EmployeeDetails(
    val id: Int,
    val name: String?,
    val age: Int,
    val address: String?,
    val salary: Double,
    val dept: String?,
    val empId: Int,
    val deptId: Int
)
