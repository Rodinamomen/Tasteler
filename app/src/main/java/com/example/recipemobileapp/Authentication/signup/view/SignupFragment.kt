package com.example.recipemobileapp.Authentication.signup.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.recipemobileapp.Authentication.signup.SignUpRepo.SignUpRepoImpl
import com.example.recipemobileapp.Authentication.signup.viewModel.SignUpViewModel
import com.example.recipemobileapp.Authentication.signup.viewModel.SignUpViewModelFactory
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.localDataSource.LocalDataSourceImpl
import com.example.recipemobileapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout

class SignupFragment : Fragment() {
    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL_KEY = "email_key"
        const val PASSWORD_KEY = "password_key"
    }
    lateinit var editor: SharedPreferences.Editor
    lateinit var sharedPreferences: SharedPreferences
    lateinit var email: TextInputLayout
    lateinit var password: TextInputLayout
    lateinit var firstname:TextInputLayout
    lateinit var lastname:TextInputLayout
    lateinit var signupbtn: Button
    lateinit var signUpViewModel: SignUpViewModel
    lateinit var loginbtn:Button
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gettingViewModelReady(requireContext())
        firstname=view.findViewById(R.id.textinput_first_name)
        lastname=view.findViewById(R.id.textinput_last_name)
        email=view.findViewById(R.id.textinput_email)
        password=view.findViewById(R.id.textinput_password)
        signupbtn=view.findViewById(R.id.button_signup)
        loginbtn=view.findViewById(R.id.button_login_signupfrag)
        signUpViewModel.isEmailExists.observe(requireActivity()){data->
                if(data){
                    MaterialAlertDialogBuilder(requireContext()).setTitle("The Account Is Signed In").setMessage("That email address is associated with a user account.").setPositiveButton("Ok", null)
                        .show()
                }else {
                    isValidData(
                        email.editText?.text.toString(),
                        firstname.editText?.text.toString(),
                        lastname.editText?.text.toString(),
                        password.editText?.text.toString()
                    )
                }
        }
        signupbtn.setOnClickListener{
            signUpViewModel.isEmailExists(email.editText?.text.toString())
        }
        loginbtn.setOnClickListener{
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }
    }
    private fun gettingViewModelReady(context: Context) {
        val signUpViewModelFactory = SignUpViewModelFactory(
            SignUpRepoImpl(LocalDataSourceImpl(context))
        )
        signUpViewModel = ViewModelProvider(this,signUpViewModelFactory)[SignUpViewModel::class.java]
    }
    private fun isValidData(email:String,firstname:String,lastname:String,password: String){
        if(isValidName(firstname)&& isValidName(lastname)&& isValidPassword(password) &&isValidEmail(email)){
            signUpViewModel.insertUser(
                User(  username = firstname, lastName = lastname, email = email, password = password))
            sharedPreferences= requireActivity().getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE)
            editor= sharedPreferences.edit()
            editor.putString(EMAIL_KEY, email)
            editor.putString(PASSWORD_KEY,password)
            editor.commit()
            Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
            view?.findNavController()?.navigate(R.id.action_signupFragment_to_homeActivity)
            requireActivity().finish()
        }
        if(!isValidName(firstname)) {
            MaterialAlertDialogBuilder(requireContext()).setTitle("Invalid Firstname").setMessage("In the first name, there must be 3 to 30 letters, either capital or small").setPositiveButton("Ok", null)
                .show()
        }
        if(!isValidName(lastname)){
            MaterialAlertDialogBuilder(requireContext()).setTitle("Invalid  Lastname").setMessage("In the Last name, there must be 3 to 30 letters, either capital or small").setPositiveButton("Ok", null)
                .show()
        }
        if(!isValidPassword(password)){
            MaterialAlertDialogBuilder(requireContext()).setTitle("Invalid Password").setMessage("A password must have between four and eight letters, both lowercase and uppercase letters, and contain special characters.").setPositiveButton("Ok", null)
                .show()
        }
        if(!isValidEmail(email)){
            Toast.makeText(context, "invalid Email", Toast.LENGTH_SHORT).show()
            MaterialAlertDialogBuilder(requireContext()).setTitle("Invalid Email").setMessage("Check that the email you entered is a valid email").setPositiveButton("Ok", null)
                .show()
        }
    }
    private fun isValidEmail(email :String):Boolean{
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return email.matches(emailRegex.toRegex())
    }
    private fun isValidName(name:String):Boolean{
        val nameRegex="^[A-Za-z]{3,30}\$"
        return name.matches(nameRegex.toRegex())
    }
    private fun isValidPassword(password:String):Boolean{
        val passwordRegex="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,8}$"
        return password.matches(passwordRegex.toRegex())
    }
}