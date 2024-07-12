package com.example.recipemobileapp.Authentication.Login.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.recipemobileapp.Authentication.Login.LoginRepo.LoginRepo
import com.example.recipemobileapp.Authentication.Login.LoginRepo.LoginRepoImp
import com.example.recipemobileapp.Authentication.Login.viewmodel.LoginViewModel
import com.example.recipemobileapp.Authentication.Login.viewmodel.LoginViewModelFactory
import com.example.recipemobileapp.Authentication.signup.SignUpRepo.SignUpRepoImpl
import com.example.recipemobileapp.Authentication.signup.viewModel.SignUpViewModel
import com.example.recipemobileapp.Authentication.signup.viewModel.SignUpViewModelFactory
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.localDataSource.LocalDataSource
import com.example.recipemobileapp.Database.localDataSource.LocalDataSourceImpl
import com.example.recipemobileapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class LoginFragment : Fragment() {
    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL_KEY = "email_key"
        const val PASSWORD_KEY = "password_key"
    }
    lateinit var editor:Editor
    lateinit var button_signup: Button
    lateinit var loginViewModel: LoginViewModel
    lateinit var email: TextInputLayout
    lateinit var password: TextInputLayout
    lateinit var loginbutton: Button
    lateinit var sharedPreferences: SharedPreferences
  //  lateinit var fragmentManager: FragmentManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences= requireActivity().getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE)
        editor=sharedPreferences.edit()
        email = view.findViewById(R.id.text_input_email_address)
        password = view.findViewById(R.id.text_input_password)
        loginbutton = view.findViewById(R.id.button_login)
        gettingViewModelReady(requireContext())
        loginViewModel.isEmailExists.observe(requireActivity()) { flag ->
        loginViewModel.userdata.observe(requireActivity()) { data ->
            if (data != null) {
                if (data) {
                    editor.putString(EMAIL_KEY,email.editText?.text.toString())
                    editor.putString(PASSWORD_KEY,password.editText?.text.toString())
                    editor.commit()
                    view.findNavController().navigate(R.id.action_loginFragment_to_homeActivity)
                   requireActivity().finish()
                } else {
                        if (flag) {
                            Toast.makeText(context, "Invalid password", Toast.LENGTH_SHORT).show()
                        } else {
                            MaterialAlertDialogBuilder(requireContext()).setTitle("The Account Is Inactive").setMessage("By signing up, you can establish an account. ").setPositiveButton("Ok", null)
                                .show()
                        }
                    }
                }
            }
        }
        loginbutton.setOnClickListener {
            loginViewModel.isUserExist(email.editText?.text.toString(), password.editText?.text.toString())
            loginViewModel.isEmailExists(email.editText?.text.toString())
        }
        button_signup = view.findViewById(R.id.button_signup)
        button_signup.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_signupFragment)

        }
    }

    private fun gettingViewModelReady(context: Context) {
        val loginViewModelFactory = LoginViewModelFactory(
            LoginRepoImp(LocalDataSourceImpl(context))
        )
        loginViewModel =
            ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]
    }
}

