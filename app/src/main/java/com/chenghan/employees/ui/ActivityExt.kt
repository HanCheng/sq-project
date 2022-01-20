package com.chenghan.employees.ui

import android.app.Activity
import com.chenghan.employees.di.Injection
import com.chenghan.employees.viewmodels.ViewModelFactory

fun Activity.getViewModelFactory() = ViewModelFactory(Injection.getEmployeeRepository())