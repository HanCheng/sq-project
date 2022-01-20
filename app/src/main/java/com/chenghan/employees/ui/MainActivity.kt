package com.chenghan.employees.ui

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chenghan.employees.R
import com.chenghan.employees.adapters.EmployeeAdapter
import com.chenghan.employees.data.Displayable
import com.chenghan.employees.data.UiState
import com.chenghan.employees.databinding.ActivityMainBinding
import com.chenghan.employees.viewmodels.EmployeesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = EmployeeAdapter()
    private lateinit var recyclerView: RecyclerView
    private lateinit var invalidText: TextView
    private lateinit var progressBar: ProgressBar

    private val employeeViewModel: EmployeesViewModel by viewModels() {
        getViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Refreshing the UI", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            employeeViewModel.refreshUI()
        }

        bindViews()
        bindViewModel()
        employeeViewModel.getValidEmployees()
    }

    private fun bindViews() {
        recyclerView = binding.employees
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        invalidText = binding.invalidText
        progressBar = binding.loading
    }

    private fun bindViewModel() {
        employeeViewModel.employeeDisplayableData.observe(this, Observer { showDisplayableList(it) })
        employeeViewModel.uiStateData.observe(this, Observer { updateLoadingScreen(it) })
        employeeViewModel.employeeErrorData.observe(this, Observer { showErrorView(it) })
        employeeViewModel.uiErrorData.observe(this, Observer { showEmptyView(it.message) })

        employeeViewModel.displayablesData.observe(this, Observer { showDisplayableList(it) })
    }

    private fun showDisplayableList(displayables: List<Displayable>) {
        if (recyclerView.visibility == GONE) {
            recyclerView.visibility = VISIBLE
        }
        if (invalidText.visibility == VISIBLE) {
            invalidText.visibility = GONE
        }
        adapter.updateDisplayables(displayables)
    }

    private fun showErrorView(message: String) {
        recyclerView.visibility = GONE
        invalidText.visibility = VISIBLE
        invalidText.text = message
    }

    private fun updateLoadingScreen(uiState: UiState) {
        progressBar.visibility = if (uiState == UiState.LOADING) VISIBLE else GONE
    }

    private fun showEmptyView(message: String) {
        recyclerView.visibility = GONE
        invalidText.visibility = VISIBLE
        invalidText.text = message
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_name -> {
                employeeViewModel.sortName()
                true
            }
            R.id.group_team -> {
                employeeViewModel.groupByTeam()
                true
            }
            R.id.group_type -> {
                employeeViewModel.groupByType()
                true
            }
            R.id.load_empty -> {
                employeeViewModel.getEmptyData()
                true
            }
            R.id.load_error -> {
                employeeViewModel.getInvalidData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}