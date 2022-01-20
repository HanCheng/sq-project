package com.chenghan.employees.data

data class HeaderItem(val header: String) : Displayable {
    override fun getEmployeeType() = DisplayType.HEADER
}
