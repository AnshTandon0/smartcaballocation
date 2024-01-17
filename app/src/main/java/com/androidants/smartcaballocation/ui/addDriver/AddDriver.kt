package com.androidants.smartcaballocation.ui.addDriver

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.androidants.smartcaballocation.R
import com.androidants.smartcaballocation.common.Constants
import com.androidants.smartcaballocation.data.model.Driver
import com.androidants.smartcaballocation.databinding.ActivityAddDriverBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddDriver : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityAddDriverBinding
    private lateinit var viewModel: AddDriverViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDriverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.continueBtn.setOnClickListener(this)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AddDriverViewModel::class.java)
        viewModel.loadingStatus.observe(this) {
            binding.progressBar.visibility = it
        }
        viewModel.geocodeResponse.observe(this) {
            lifecycleScope.launch(Dispatchers.IO + Constants.coroutineExceptionHandler) {
                val driver = Driver(
                    binding.editTextName.text.toString(),
                    binding.editTextEmail.text.toString(),
                    binding.editTextPhone.text.toString(),
                    binding.editTextLatitude.text.toString().toFloat(),
                    binding.editTextLongitude.text.toString().toFloat(),
                    it.features[0].properties?.country.toString(),
                    it.features[0].properties?.state.toString(),
                    it.features[0].properties?.city.toString(),
                    it.features[0].properties?.postcode.toString()
                )
                viewModel.setDriver(driver)
            }
        }
        viewModel.setDriverStatus.observe(this) {
            if (it) {
                Toast.makeText(this, "Driver Added Successfully", Toast.LENGTH_SHORT).show()
                clearFields()
            } else
                Toast.makeText(this, "Error while Inserting . Try again", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkDetails() {
        if (binding.editTextName.text == null) {
            binding.editTextName.error = "Enter name"
            return
        } else if (binding.editTextPhone.text == null) {
            binding.editTextPhone.error = "Enter phone"
            return
        } else if (binding.editTextEmail.text == null) {
            binding.editTextEmail.error = "Enter email"
            return
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.editTextEmail.text.toString())
                .matches()
        ) {
            binding.editTextEmail.error = "Enter correct email"
            return
        } else if (binding.editTextLatitude.text == null) {
            binding.editTextLatitude.error = "Enter Latitude"
            return
        } else if (binding.editTextLongitude.text == null) {
            binding.editTextLongitude.error = "Enter Longitude"
            return
        }
        lifecycleScope.launch(Dispatchers.IO + Constants.coroutineExceptionHandler) {
            viewModel.reverseGeocode(
                binding.editTextLatitude.text.toString().toDouble(),
                binding.editTextLongitude.text.toString().toDouble(), Constants.API_KEY
            )
        }
    }

    private fun clearFields() {
        binding.editTextLatitude.text.clear()
        binding.editTextName.text.clear()
        binding.editTextPhone.text.clear()
        binding.editTextEmail.text.clear()
        binding.editTextLongitude.text.clear()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.continueBtn -> {
                checkDetails()
            }
        }
    }

}