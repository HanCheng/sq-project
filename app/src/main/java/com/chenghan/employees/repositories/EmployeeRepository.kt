package com.chenghan.employees.repositories

import com.chenghan.employees.data.Employee
import com.chenghan.employees.network.EmployeeApi
import io.reactivex.rxjava3.core.Observable

class EmployeeRepository constructor(private val employeeApi: EmployeeApi) {

    fun getValidEmployees(): Observable<List<Employee>> =
        employeeApi.getEmployees().map { it.employees }

    fun getInValidEmployees(): Observable<List<Employee>> =
        employeeApi.getEmployeesMalformed().map { it.employees }

    fun getEmptyEmployees(): Observable<List<Employee>> =
        employeeApi.getEmptyEmployees().map { it.employees }
}