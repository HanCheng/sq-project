package com.chenghan.employees.data

/**
 * Interface class for differentiating UI data in adapter
 */
interface Displayable {
    fun getEmployeeType(): DisplayType
}