package com.example.infonavit.ui.login

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.infonavit.MainActivity
import com.example.infonavit.data.network.RemoteDataSource
import com.example.infonavit.databinding.ActivityLoginBinding
import com.example.infonavit.repository.RepositoryImpl
import com.example.infonavit.ui.VMFactory
import com.example.infonavit.utils.UserPreferences
import com.example.infonavit.vo.Resource


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userPreferences: UserPreferences

    // inyeccion de dependencia para pasar el repo al view model
    private val  viewModel by viewModels<LoginViewModel> { VMFactory(RepositoryImpl(RemoteDataSource())) } // se puede sustituir por DAGGER O HILT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupObservers()

        initView()
        setupListeners()

    }

    fun initView(){
        binding.btnEntrar.isEnabled = false
    }

    fun setupListeners(){
        binding.txtCorreo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                text: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                validateTextInputsEmpty()
                Log.e("text", text.toString())
            }
        })

        binding.txtPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                text: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                validateTextInputsEmpty()
                Log.e("text", text.toString())
            }
        })

        binding.btnEntrar.setOnClickListener {
            login(binding.txtCorreo.text.toString(), binding.txtPassword.text.toString())
        }

        binding.txtPassword.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                login(binding.txtCorreo.text.toString(), binding.txtPassword.text.toString())
            }
            false
        }
    }


    fun login(email: String, password: String){
        viewModel.login(email, password)
    }


    fun validateTextInputsEmpty(){
        val validationEmpty = (!TextUtils.isEmpty(binding.txtCorreo.text)) && (!TextUtils.isEmpty(
            binding.txtPassword.text
        ))
        binding.btnEntrar.isEnabled = validationEmpty
    }


    fun setupObservers(){

        // login observer
        viewModel.loginResponse.observe(this, { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.e("LOADING: ", "Ingresando...")
                }
                is Resource.Success -> {

                    userPreferences = UserPreferences(applicationContext)

                    if (!TextUtils.isEmpty(result.data.token)) {
                        userPreferences.saveToken(result.data.token)
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    } else {
                        showAlert("Intente nuevamente", "Usuario y contraseña incorrectos")
                    }

                }
                is Resource.Failure -> {

                    showAlert("Intente nuevamente", "Usuario y contraseña incorrectos")
                }
            }
        })
    }





    private fun showAlert(message: String, title: String){

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.setMessage(message).setTitle(title)

        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()

        dialog.show()
    }










}