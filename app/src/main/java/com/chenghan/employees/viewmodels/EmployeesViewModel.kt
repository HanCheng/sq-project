package com.chenghan.employees.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chenghan.employees.data.*
import com.chenghan.employees.exceptions.EmptyDataException
import com.chenghan.employees.repositories.EmployeeRepository
import com.chenghan.employees.utils.Utils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

const val ERROR_ON_DATA = "ERROR on loading Employee list"
const val EMPTY_ON_DATA = "EMPTY Employee list"

class EmployeesViewModel constructor(private val repository: EmployeeRepository) : ViewModel() {

    private val _employeeDisplayableData = MutableLiveData<List<EmployeeItem>>()
    private val _displayablesData = MutableLiveData<List<Displayable>>()

    private val _employeeErrorData = MutableLiveData<String>()
    private val _uiStateData = MutableLiveData<UiState>()
    private val _uiErrorData = MutableLiveData<UiErrorData>()

    private val compositeDisposable = CompositeDisposable()

    val employeeDisplayableData: MutableLiveData<List<EmployeeItem>>
        get() = _employeeDisplayableData
    val employeeErrorData: MutableLiveData<String>
        get() = _employeeErrorData
    val uiStateData: MutableLiveData<UiState>
        get() = _uiStateData

    /*
       Data displayable on the UI, including header and employee data
     */
    val displayablesData: MutableLiveData<List<Displayable>>
        get() = _displayablesData

    val uiErrorData: MutableLiveData<UiErrorData>
        get() = _uiErrorData

    fun getValidEmployees() {
        _uiStateData.value = UiState.LOADING
        repository.getValidEmployees()
            .map { it.map { Utils.convertToEmployeeItem(it) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { displayables ->
                    _uiStateData.value = UiState.FINISHED
                    _employeeDisplayableData.value = displayables
                },
                { error ->
                    _uiStateData.value = UiState.ERROR
                }
            )
            .addTo(compositeDisposable)
    }

    fun getEmptyData() {
        _uiStateData.value = UiState.LOADING
        repository.getEmptyEmployees()
            .map { it.map { Utils.convertToEmployeeItem(it) } }
            .flatMap { employees ->
                if (employees.isEmpty()) {
                    Observable.error(EmptyDataException(EMPTY_ON_DATA))
                } else {
                    Observable.just(employees)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { displayables ->
                    _uiStateData.value = UiState.FINISHED
                    _employeeDisplayableData.value = displayables
                },
                { error ->
                    _uiStateData.value = UiState.ERROR
                    _uiErrorData.value = UiErrorData(error.message ?: " ")
                }
            )
            .addTo(compositeDisposable)
    }

    fun getInvalidData() {
        _uiStateData.value = UiState.LOADING
        repository.getInValidEmployees()
            .map { it.map { Utils.convertToEmployeeItem(it) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { employees ->
                    _uiStateData.value = UiState.FINISHED
                },
                { error ->
                    _uiStateData.value = UiState.ERROR
                    _uiErrorData.value = UiErrorData(error.message ?: " ")
                    _employeeErrorData.value = ERROR_ON_DATA
                }
            )
            .addTo(compositeDisposable)
    }

    fun refreshUI() {
        _uiStateData.value = UiState.LOADING
        Observable.timer(2500, TimeUnit.MILLISECONDS)
            .flatMap { time -> repository.getValidEmployees() }
            .map { it.map { Utils.convertToEmployeeItem(it) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { displayables ->
                    _uiStateData.value = UiState.FINISHED
                    _employeeDisplayableData.value = displayables
                },
                { error ->
                    _uiStateData.value = UiState.ERROR
                }
            )
            .addTo(compositeDisposable)
    }

    fun sortName() {
        _employeeDisplayableData.value = _employeeDisplayableData.value?.let {
            it.sortedBy { it.fullName }
        }
    }

    fun groupByTeam() {
        _displayablesData.value = _employeeDisplayableData.value?.let {
            val map = it.groupingBy { it.team }
                .fold(listOf<Displayable>()) {
                    acc, element -> acc + element
                }
            val displayables = arrayListOf<Displayable>()
            map.entries.forEach { entry ->
                displayables.add(HeaderItem(entry.key))
                displayables.addAll(entry.value)
            }
            displayables
        }
    }

    fun groupByType() {
        _displayablesData.value = _employeeDisplayableData.value?.let {
            val map = it.groupingBy { it.employeeType }
                .fold(listOf<Displayable>()) {
                        acc, element -> acc + element
                }
            val displayables = arrayListOf<Displayable>()
            map.entries.forEach { entry ->
                displayables.add(HeaderItem(entry.key.name))
                displayables.addAll(entry.value)
            }
            displayables
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}