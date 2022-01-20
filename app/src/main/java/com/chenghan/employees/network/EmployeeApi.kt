package com.chenghan.employees.network

import com.chenghan.employees.data.Employees
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface EmployeeApi {

    @GET("employees.json")
    fun getEmployees() : Observable<Employees>

    @GET("employees_malformed.json")
    fun getEmployeesMalformed() : Observable<Employees>

    @GET("employees_empty.json")
    fun getEmptyEmployees() : Observable<Employees>
}