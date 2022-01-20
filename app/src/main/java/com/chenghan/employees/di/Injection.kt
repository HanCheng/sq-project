package com.chenghan.employees.di

import com.chenghan.employees.network.EmployeeService
import com.chenghan.employees.repositories.EmployeeRepository

object Injection {

    private val employeeService = EmployeeService()
    private var repository: EmployeeRepository? = null

    fun getEmployeeRepository() = repository ?: EmployeeRepository(employeeService.api)
}