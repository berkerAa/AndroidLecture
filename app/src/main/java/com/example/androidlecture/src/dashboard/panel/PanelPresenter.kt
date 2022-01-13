package com.example.androidlecture.src.dashboard.panel

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.os.bundleOf
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.androidlecture.R
import com.example.androidlecture.app_modules.presenter.Presenter
import com.example.androidlecture.databinding.DashboardActivityPanelDatabindingBinding
import com.example.androidlecture.databinding.StudentActivityLoginDatabindingBinding
import com.example.androidlecture.src.dashboard.DashboardBridge
import com.example.androidlecture.src.dashboard.Details
import com.example.androidlecture.src.dashboard.Panel
import com.example.androidlecture.src.user.UserBridge
import com.example.androidlecture.src.user.studentLogin.StudentLoginViewModel

class PanelPresenter: Presenter<PanelViewModel, DashboardActivityPanelDatabindingBinding>() {
    private val viewModel: PanelViewModel by injectActivityVIewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as DashboardBridge).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = binding<DashboardActivityPanelDatabindingBinding>(
            inflater = inflater,
            resId = R.layout.dashboard_activity_panel_databinding,
            container = container).apply {
            lifecycleOwner = this@PanelPresenter
        }
        return viewBinding.root
    }

    override fun initUI() {
        val items = resources.getStringArray(R.array.departments);
        val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_textview, R.id.textview, items)
        (viewBinding.departmentPanelDropdown.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    override fun observeOn() {
        viewBinding.searchButton.setOnClickListener {
            (activity as DashboardBridge).navigation(
                Details(),
                bundle = bundleOf(PANEL_SELECTION to viewBinding.departmentPanelDropdown.editText!!.text.toString())
            )
        }
    }

    companion object {
        const val PANEL_SELECTION = "panel_selection"
    }
}