package com.androidants.smartcaballocation.ui.authentication.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.androidants.smartcaballocation.ui.main.MainActivity
import com.androidants.smartcaballocation.R
import com.androidants.smartcaballocation.common.Constants
import com.androidants.smartcaballocation.data.model.User
import com.androidants.smartcaballocation.databinding.ActivityRegisterBinding
import com.androidants.smartcaballocation.ui.authentication.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() ,OnClickListener {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var viewModel : RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        binding.continueBtn.setOnClickListener(this)
        binding.login.setOnClickListener(this)
    }

    private fun checkDetails () {
        if (binding.editTextName.text == null) {
            binding.editTextName.error = "Enter name"
            return
        } else if (binding.editTextPhone.text == null) {
            binding.editTextPhone.error = "Enter phone"
            return
        } else if (binding.editTextEmail.text == null) {
            binding.editTextEmail.error = "Enter email"
            return
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.editTextEmail.text.toString()).matches()){
            binding.editTextEmail.error = "Enter correct email"
            return
        } else if (binding.editTextPassword.text.toString().equals("")) {
            binding.editTextPassword.error = "Enter password"
            return
        } else if (binding.editTextPassword.text.toString().length < 6) {
            binding.editTextPassword.error = "must be > 6 characters"
            return
        }
        lifecycleScope.launch(Dispatchers.IO + Constants.coroutineExceptionHandler) {
            viewModel.register(binding.editTextEmail.text.toString() , binding.editTextPassword.text.toString())
        }
    }

    override fun onClick(view: View?) {
        when( view?.id )
        {
            R.id.login -> {
                startActivity(Intent(this@RegisterActivity , LoginActivity::class.java))
                finish()
            }

            R.id.continueBtn -> {
                checkDetails()
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        viewModel.loadingStatus.observe(this){
            binding.progressBar.visibility = it
        }
        viewModel.registerStatus.observe(this){
            if (!it)
                Toast.makeText(this , "Error while registering . Try again" , Toast.LENGTH_SHORT).show()
            else {
                lifecycleScope.launch(Dispatchers.IO + Constants.coroutineExceptionHandler) {
                    viewModel.createUser(getUser())
                }
            }
        }
        viewModel.createUserStatus.observe(this){
            if (!it)
                Toast.makeText(this , "Error while registering . Try again" , Toast.LENGTH_SHORT).show()
            else {
                startActivity(Intent(this , MainActivity::class.java))
                finish()
            }
        }
    }

    private fun getUser(): User {
        return User( binding.editTextName.text.toString() ,
            binding.editTextEmail.text.toString() ,
            binding.editTextPhone.text.toString() ,
            0
        )
    }

}