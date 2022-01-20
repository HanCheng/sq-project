package com.chenghan.employees.data

data class EmployeeItem(
    val uuid: String,
    val fullName: String,
    val phoneNUmber: String?,
    val emailAddress: String,
    val biography: String?,
    val photoUrlSmall: String?,
    val photoUrlLarge: String?,
    val team: String,
    val employeeType: EmployeeType,
) : Displayable {
    override fun getEmployeeType() = DisplayType.EMPLOYEE
}
