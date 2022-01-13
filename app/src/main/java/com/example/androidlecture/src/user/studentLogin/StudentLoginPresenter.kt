package com.example.androidlecture.src.user.studentLogin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.example.androidlecture.R
import com.example.androidlecture.app_modules.presenter.Presenter
import com.example.androidlecture.databinding.StudentActivityLoginDatabindingBinding
import com.example.androidlecture.src.user.Dashboard
import com.example.androidlecture.src.user.Register
import com.example.androidlecture.src.user.UserBridge
import com.example.androidlecture.src.user.cases.CorrectValue
import com.example.androidlecture.src.user.cases.CredentialsError
import com.example.androidlecture.src.user.cases.IncorrectValue
import com.example.androidlecture.src.user.cases.Success
import com.example.androidlecture.src.user.data.StudentLoginModel

class StudentLoginPresenter: Presenter<StudentLoginViewModel, StudentActivityLoginDatabindingBinding>() {

    private val viewModel: StudentLoginViewModel by injectActivityVIewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as UserBridge).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = binding<StudentActivityLoginDatabindingBinding>(
            inflater = inflater,
            resId = R.layout.student_activity_login_databinding,
            container = container).apply {
            lifecycleOwner = this@StudentLoginPresenter
        }
        return viewBinding.root
    }

    override fun observeOn(){
        viewModel.studentIdStatusLiveData.observe(viewLifecycleOwner, {
            status ->
            when (status){
                IncorrectValue -> viewBinding.idEditText.error = getString(R.string.error)
                CorrectValue -> viewBinding.idEditText.error = null
            }
        })
        viewBinding.studentIdEdittextField.doOnTextChanged { text, start, before, count ->

            when (start){
                9 -> viewModel.setStudentIdStatus(CorrectValue)
                else -> viewModel.setStudentIdStatus(IncorrectValue)
            }

        }

        viewBinding.loginButtonStudent.setOnClickListener {
            compositeDisposable.add(
                viewModel.onLogin(
                    StudentLoginModel(
                        studentId = viewBinding.studentIdEdittextField.text.toString(),
                        studentPassword = viewBinding.passwordEdittextField.text.toString()
                    )
                )
                    .subscribe { status ->
                        Log.d(STUDENT_TAG, status.toString())
                        when (status)
                        {
                            Success ->  (activity as UserBridge).navigation(Dashboard())
                            CredentialsError -> viewBinding.apply {
                                passwordEditText.error = getString(R.string.error)
                                idEditText.error = getString(R.string.error)
                            }
                            else -> {}
                        }
                    }
            )
        }
        viewBinding.registerButton.setOnClickListener {
            (activity as UserBridge).navigation(Register())
        }
    }


    companion object {
        const val STUDENT_TAG = "STUDENT PRESENTER"
    }
}