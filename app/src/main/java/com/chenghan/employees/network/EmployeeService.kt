package com.chenghan.employees.network

import com.chenghan.employees.utils.API_END_POINT
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class EmployeeService {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(API_END_POINT)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(EmployeeApi::class.java)
}