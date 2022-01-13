package com.example.androidlecture.src.dashboard.details

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlecture.R
import com.example.androidlecture.app_modules.presenter.Presenter
import com.example.androidlecture.app_modules.sequilezer.model.StudentModel
import com.example.androidlecture.databinding.DashboardActivityDetailsDatabindingBinding
import com.example.androidlecture.databinding.DashboardActivityPanelDatabindingBinding
import com.example.androidlecture.databinding.ListItemCardBinding
import com.example.androidlecture.src.dashboard.DashboardBridge
import com.example.androidlecture.src.dashboard.adapter.DashboardRecyclerViewAdapter
import com.example.androidlecture.src.dashboard.cases.*
import com.example.androidlecture.src.dashboard.data.DetailsModel
import com.example.androidlecture.src.dashboard.panel.PanelPresenter
import com.example.androidlecture.src.user.cases.Success
import com.example.androidlecture.src.user.data.RegisterModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar

class DetailsPresenter: Presenter<DetailsViewModel, DashboardActivityDetailsDatabindingBinding>() {
    private val viewModel: DetailsViewModel by injectActivityVIewModels()
    private lateinit var departmentSelection: String
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as DashboardBridge).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        departmentSelection = requireArguments().getString(PanelPresenter.PANEL_SELECTION)!!
        Log.d(DETAILS_PRESENTER, departmentSelection)
        viewBinding = binding<DashboardActivityDetailsDatabindingBinding>(
            inflater = inflater,
            resId = R.layout.dashboard_activity_details_databinding,
            container = container).apply {
            lifecycleOwner = this@DetailsPresenter
        }
        setAdapter(container)
        return viewBinding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initUI() {

        setDropDowns()



        viewBinding.addEntryButton.setOnClickListener {
            if (viewModel.checkAccess()) popUpRegister() else Snackbar.make(viewBinding.root, getString(R.string.access_error), 300).show()
        }
        viewBinding.deleteEntryButton.setOnClickListener {
            if (viewModel.checkAccess()) onDeleteUser() else Snackbar.make(viewBinding.root, getString(R.string.access_error), 300).show()
        }

        viewBinding.passowodPopupLayout.setAccountButtonStudent.setOnClickListener {
            onPasswordPopUpSuccess()
        }
        viewModel.queryStatus.observe(
            viewLifecycleOwner
        ){ status ->
            when ( status ){
                is InProgress -> {}
                is FinishedError -> {}
                is FinishedSuccess -> {
                    (viewBinding.listRecyclerId.adapter as DashboardRecyclerViewAdapter).apply {
                        items = status.data
                        Log.d(DETAILS_PRESENTER, items.toString())
                        notifyDataSetChanged()
                    }
                }
            }
        }
        viewModel.entryStatus.observe(
            viewLifecycleOwner
        ){ status ->
            when( status ){
                is Init -> viewBinding.cardRegister.visibility = View.GONE
                is EntryBody -> viewBinding.registerBodyStudentLayoutContainer.visibility = View.VISIBLE
                is PasswordPopUp ->viewBinding.passwordPopupContainer.visibility = View.VISIBLE
            }
        }

        viewBinding.cardRegister.setOnFocusChangeListener { v, hasFocus ->
            Log.d(DETAILS_PRESENTER, hasFocus.toString())
            when ( hasFocus )
            {
                true -> {}
                false -> viewModel.setEntryStatus(Init)
            }
        }
    }

    override fun observeOn() {
        refreshTable()

    }

    private fun setAdapter(container: ViewGroup?) {
        val verticalLayoutManager =  LinearLayoutManager( context, LinearLayoutManager.VERTICAL, false )
        viewBinding.listRecyclerId.setHasFixedSize( true )
        viewBinding.listRecyclerId.layoutManager = verticalLayoutManager
        viewBinding.listRecyclerId.adapter = DashboardRecyclerViewAdapter( requireContext() , arrayListOf())
    }

    private fun popUpRegister(){
        viewBinding.apply {
            this.cardRegister.visibility = View.VISIBLE
            viewModel.entryStatus.value
            listRecyclerId.setBackgroundColor(resources.getColor(R.color.semi_trans))
            this.proceedButton.setOnClickListener {
                when (viewModel.entryStatus.value)
                {
                    is ProfileSelection -> {
                        profileSelection.visibility = View.GONE
                        viewModel.setEntryStatus(EntryBody)
                    }
                    is EntryBody -> {
                        registerBodyStudentLayoutContainer.visibility = View.GONE
                        proceedButton.visibility = View.GONE
                        viewModel.setEntryStatus(PasswordPopUp)
                    }
                }
            }
        }
    }

    private fun setDropDowns(){
        var items = resources.getStringArray(R.array.profile);
        var adapter = ArrayAdapter(requireContext(), R.layout.drop_down_textview, R.id.textview, items)
        (viewBinding.profileSelection.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        items = resources.getStringArray(R.array.departments);
        adapter = ArrayAdapter(requireContext(), R.layout.drop_down_textview, R.id.textview, items)
        (viewBinding.registerBodyStudentLayout.departmentDropdown.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun onPasswordPopUpSuccess(){
        viewBinding.passowodPopupLayout.setAccountButtonStudent.setOnClickListener {
            viewBinding.passwordPopupContainer.visibility = View.GONE
            viewBinding.cardRegister.visibility = View.GONE
            compositeDisposable.add(viewModel.addUser(
                RegisterModel(
                    name = viewBinding.registerBodyStudentLayout.studentNameEdittextField.text.toString(),
                    surname = viewBinding.registerBodyStudentLayout.studentSurnameEdittextField.text.toString(),
                    email = viewBinding.registerBodyStudentLayout.studentEmailEdittextField.text.toString(),
                    birthDate = viewBinding.registerBodyStudentLayout.studentBirthdateEdittextField.text.toString(),
                    department = viewBinding.registerBodyStudentLayout.departmentDropdown.editText?.text.toString(),
                    password = viewBinding.passowodPopupLayout.passwordEdittextField.text.toString(),
                    isLecturer = viewBinding.profileSelection.editText?.text.toString() == "Lecturer"
                )
            ).subscribe {
                refreshTable()
            }
            )
        }
    }

    private fun onDeleteUser(){
        viewBinding.cardDelete.visibility = View.VISIBLE
        viewBinding.deleteBodyLayout.deleteButton.setOnClickListener {

            compositeDisposable.add(
                viewModel.deleteUser(viewBinding.deleteBodyLayout.deleteUserIdField.text.toString()).subscribe {
                    when (it){
                        is Success -> Snackbar.make(viewBinding.root, getString(R.string.delete_success), 300).show()
                        else -> Snackbar.make(viewBinding.root, getString(R.string.delete_error), 300).show()
                    }
                }
            )
            refreshTable()
            viewBinding.cardDelete.visibility = View.GONE
        }
        viewBinding.deleteBodyLayout.cancelButton.setOnClickListener {
            viewBinding.cardDelete.visibility = View.GONE
        }

    }
    private fun refreshTable(){
        compositeDisposable.add(
            viewModel.pullItems(departmentSelection).subscribe {
                viewModel.setQueryStatus(it)
            }
        )
    }

    companion object {
        const val DETAILS_PRESENTER = "Details Presenter"
        const val DURATION:Int = 100
    }
}