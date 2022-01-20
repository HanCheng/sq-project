package com.chenghan.employees.utils

import com.chenghan.employees.data.Employee
import com.chenghan.employees.data.EmployeeItem
import com.chenghan.employees.data.EmployeeType

object Utils {

    fun convertToEmployeeItem(employee: Employee) : EmployeeItem {
        return EmployeeItem(
            employee.uuid,
            employee.fullName,
            employee.phoneNumber,
            employee.email,
            employee.biography,
            employee.photoUrlSmall,
            employee.photoUrlLarge,
            employee.team,
            EmployeeType.valueOf(employee.employeeType)
        )
    }
}