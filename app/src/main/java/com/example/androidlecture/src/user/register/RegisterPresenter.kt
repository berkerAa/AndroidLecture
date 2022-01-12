package com.example.androidlecture.src.user.register

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import com.example.androidlecture.R
import com.example.androidlecture.app_modules.presenter.Presenter
import com.example.androidlecture.databinding.StudentActivityLoginDatabindingBinding
import com.example.androidlecture.databinding.StudentActivityRegisterDatabindingBinding
import com.example.androidlecture.src.user.Login
import com.example.androidlecture.src.user.UserBridge
import com.example.androidlecture.src.user.cases.CredentialsError
import com.example.androidlecture.src.user.cases.Success
import com.example.androidlecture.src.user.cases.SuccessWithData
import com.example.androidlecture.src.user.data.RegisterModel
import com.example.androidlecture.src.user.studentLogin.StudentLoginPresenter
import com.example.androidlecture.src.user.studentLogin.StudentLoginViewModel
import java.lang.AssertionError

class RegisterPresenter : Presenter<RegisterViewModel, StudentActivityRegisterDatabindingBinding>() {

    private val viewModel: RegisterViewModel by injectActivityVIewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as UserBridge).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = binding<StudentActivityRegisterDatabindingBinding>(
            inflater = inflater,
            resId = R.layout.student_activity_register_databinding,
            container = container).apply {
            lifecycleOwner = this@RegisterPresenter
        }
        return viewBinding.root
    }

    override fun initUI() {
        val items = resources.getStringArray(R.array.profile);
        val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_textview, R.id.textview, items)
        (viewBinding.profileSelection.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    override fun observeOn() {

        ( viewBinding.profileSelection.editText as AutoCompleteTextView ).setOnItemClickListener { parent, view, position, id ->

            viewBinding.profileSelection.visibility = View.GONE
            viewBinding.registerBodyStudent.visibility = View.VISIBLE
            val items = resources.getStringArray(R.array.departments);
            val adapter =
                ArrayAdapter(requireContext(), R.layout.drop_down_textview, R.id.textview, items)
            (viewBinding.registerBodyStudentLayout.departmentDropdown.editText as? AutoCompleteTextView)?.setAdapter(
                adapter
            )
            viewModel.setProfile(position != 0)
        }

        viewBinding.registerBodyStudentLayout.studentEmailEdittextField.doOnTextChanged { text, start, before, count ->

            when ( Regex("@test").find(text.toString()) )
            {
                null -> viewBinding.registerBodyStudentLayout.studentEmailEdittextField.error = getString(R.string.error)
                else -> viewBinding.registerBodyStudentLayout.studentEmailEdittextField.error = null
            }
        }

        viewBinding.registerButton.setOnClickListener {
            try {
                viewBinding.registerBodyStudentLayout.apply {
                    viewModel.apply {
                        name = studentNameEdittextField.text.toString()
                        assert( name != ""  )
                        surname = studentSurnameEdittextField.text.toString()
                        assert( surname != ""  )
                        birthDate = studentBirthdateEdittextField.text.toString()
                        assert( birthDate != ""  )
                        department = departmentDropdown.editText!!.text.toString()
                        assert( department != ""  )
                        email = studentEmailEdittextField.text.toString()
                        assert( email != ""  )

                    }
                    onValidated()
                }

            }
            catch (assert: AssertionError)
            {
                Log.d(REGISTER_TAG, "AssertionError error")
            }
        }
    }

    private fun onValidated() {
        viewBinding.apply {
            registerButton.visibility = View.GONE
            registerBodyStudent.visibility = View.GONE
            passwordPopup.visibility = View.VISIBLE
            passowodPopupLayout.confirmPasswordEdittextField.doOnTextChanged { text, start, before, count ->
                when(text.toString() == passowodPopupLayout.passwordEdittextField.text.toString())
                {
                    true -> {
                        passowodPopupLayout.confirmPasswordEditText.error = null


                    }
                    false -> {
                        passowodPopupLayout.confirmPasswordEditText.error = getString(R.string.confirm_password_error)
                    }

                }
            }
            passowodPopupLayout.setAccountButtonStudent.setOnClickListener{
                compositeDisposable.add(viewModel.onRegister(
                    RegisterModel(
                        name = viewModel.name,
                        surname = viewModel.surname,
                        birthDate = viewModel.birthDate,
                        password = passowodPopupLayout.confirmPasswordEdittextField.text.toString(),
                        department = viewModel.department,
                        email = viewModel.email,
                        isLecturer = viewModel.isLecturer)
                )
                    .subscribe { status ->
                        Log.d(REGISTER_TAG, status.toString())
                        when (status)
                        {
                            Success ->  Log.d(REGISTER_TAG, status.toString())
                            is SuccessWithData -> onFinished(status.data as Int)
                            CredentialsError -> viewBinding.apply {
                            }
                            else -> {}
                        }
                    }
                )
            }
        }
    }

    private fun onFinished(id: Int){
        viewBinding.apply {
            passwordPopup.visibility = View.GONE
            card.visibility = View.VISIBLE
            viewBinding.registerIdTextfield.text = id.toString()
            viewBinding.proceedButton.setOnClickListener {
                (activity as UserBridge).navigation(Login())
            }
        }
    }

    companion object {
        const val REGISTER_TAG = "REGISTER PRESENTER"
    }

}