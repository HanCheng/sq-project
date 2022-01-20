package com.chenghan.employees.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chenghan.employees.data.DisplayType
import com.chenghan.employees.data.Displayable
import com.chenghan.employees.data.EmployeeItem
import com.chenghan.employees.data.HeaderItem
import com.chenghan.employees.databinding.HeaderItemBinding
import com.chenghan.employees.databinding.ItemEmployeeBinding

/**
 * Adapter that is able to show different UI based on the {DisplayableType}
 * TODO Separate different UI view holders into different file
 */

class EmployeeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val displayables = ArrayList<Displayable>()

    class EmployeeViewHolder(private val binding: ItemEmployeeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val avatar = binding.avatar
        private val name = binding.name
        private val team = binding.team

        fun bind(employee: EmployeeItem) {
            Glide
                .with(binding.root)
                .load(employee.photoUrlSmall)
                .apply(RequestOptions().override(250, 250))
                .into(avatar)
            name.text = employee.fullName
            team.text = employee.team
        }
    }

    class HeaderViewHolder(private val binding: HeaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val headerText = binding.headerText

        fun bind(headerItem: HeaderItem) {
            headerText.text = headerItem.header
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == DisplayType.HEADER.ordinal) {
            val itemHeaderBinding =
                HeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HeaderViewHolder(itemHeaderBinding)
        } else {
            val itemEmployeeBinding =
                ItemEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return EmployeeViewHolder(itemEmployeeBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val displayable = displayables[position]
        if (holder is EmployeeViewHolder && displayable is EmployeeItem) {
            holder.bind(displayable)
            return
        }
        if (holder is HeaderViewHolder && displayable is HeaderItem) {
            holder.bind(displayable)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return displayables[position].getEmployeeType().ordinal
    }

    override fun getItemCount() = displayables.size

    fun updateDisplayables(displayableList: List<Displayable>) {
        displayables.clear()
        displayables.addAll(displayableList)
        notifyDataSetChanged()
    }
}