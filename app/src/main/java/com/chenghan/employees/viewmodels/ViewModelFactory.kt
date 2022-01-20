package com.chenghan.employees.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chenghan.employees.repositories.EmployeeRepository
import java.lang.IllegalArgumentException

class ViewModelFactory constructor(private val repository: EmployeeRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) = with(modelClass) {
        when {
            isAssignableFrom(EmployeesViewModel::class.java) ->
                EmployeesViewModel(repository)
            else ->
                throw IllegalArgumentException("Unable to find a match with ${modelClass}")

        }
    } as T
}