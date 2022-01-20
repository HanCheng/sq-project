package com.chenghan.employees.data

import com.google.gson.annotations.SerializedName

data class Employees(
    @SerializedName("employees") val employees: List<Employee>
)