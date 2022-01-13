package com.example.androidlecture.src.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlecture.app_modules.sequilezer.model.StudentModel
import com.example.androidlecture.databinding.ListItemCardBinding
import com.example.androidlecture.src.dashboard.data.DetailsModel

class DashboardRecyclerViewAdapter(private val context: Context, var items: ArrayList<StudentModel>): RecyclerView.Adapter<DashboardItemView>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardItemView {
        return DashboardItemView(
            ListItemCardBinding.inflate(LayoutInflater.from(context), null, false)
        )
    }

    override fun onBindViewHolder(holder: DashboardItemView, position: Int) {
        holder.setCard(items[position])
    }

    override fun getItemCount(): Int = items.size

}

