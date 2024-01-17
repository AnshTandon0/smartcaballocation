package com.androidants.smartcaballocation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.androidants.smartcaballocation.ui.main.MainActivity
import com.androidants.smartcaballocation.R
import com.androidants.smartcaballocation.ui.authentication.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Thread.sleep

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel : SplashViewModal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(SplashViewModal::class.java)
        viewModel.authStatus.observe(this){
            initAnimation(it)
        }
    }

    private fun initAnimation(auth : Boolean) {
        Handler(Looper.getMainLooper()).postDelayed({
            if ( auth )
                startActivity(Intent(this , MainActivity::class.java))
            else
                startActivity(Intent(this , RegisterActivity::class.java))
            finish()
        }, 3700)
    }
}