package com.example.androidlecture.src.dashboard.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlecture.R
import com.example.androidlecture.app_modules.sequilezer.model.StudentModel
import com.example.androidlecture.databinding.ListItemCardBinding
import com.example.androidlecture.src.dashboard.data.DetailsModel

class DashboardItemView constructor(private val view: ListItemCardBinding) : RecyclerView.ViewHolder(view.root) {

    fun setCard(item: StudentModel) {
        view.entryName.text = view.root.context.getString(R.string.name_format, item.name, item.surname)
        view.entryId.text =  view.root.context.getString(R.string.id, item.id.toString())
        view.entryDepartment.text =  view.root.context.getString(R.string.entry_department, item.department)
        view.entryEmail.text = view.root.context.getString(R.string.entry_email, item.email)
    }
}