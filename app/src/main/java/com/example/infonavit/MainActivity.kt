package com.example.infonavit


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.infonavit.data.network.RemoteDataSource
import com.example.infonavit.repository.RepositoryImpl
import com.example.infonavit.ui.VMFactory
import com.example.infonavit.ui.MainViewModel
import com.example.infonavit.ui.login.LoginActivity
import com.example.infonavit.utils.UserPreferences
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(){

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    // inyeccion de dependencia para pasar el repo al view model
    private val  viewModel by viewModels<MainViewModel> { VMFactory(RepositoryImpl(RemoteDataSource())) }

    override fun onCreate(savedInstanceState: Bundle?) {
        //setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpObservers()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        //var navController = findNavController(R.id.nav_host_fragment)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
//        NavigationUI.setupWithNavController(navView, navController)
//



        navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {

                    val current = navController.currentDestination
                    if (current?.id == R.id.nav_home) {
                        drawerLayout.closeDrawer(GravityCompat.START, false)
                    } else {
                        navController.navigate(R.id.nav_home)
                    }

                }
                R.id.nav_logout -> {
                    drawerLayout.closeDrawer(GravityCompat.START, false)
                    showAlert("¿Desea cerrar sesión?", "Cerrar sesión")

                }
            }
            false
        })



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
//            setOf(R.id.nav_home, R.id.nav_logout),
                setOf(R.id.nav_home),
                drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        //navView.setupWithNavController(navController)
    }



    fun setUpObservers(){
        viewModel.logoutResponse.observe(this, Observer {
            it.equals(true).let {
                UserPreferences(applicationContext).deleteToken()
//                startActivity(Intent(this, LoginActivity::class.java))
//                finish()

                startActivity(Intent(baseContext, LoginActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP))
                finish()

//                val intent: Intent = Intent(this, LoginActivity::class.java)
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)// this will clear all the stack
//                intent.putExtra("Exit me", true);
//                startActivity(intent);
//                finish();

            }
        })
    }

    private fun showAlert(message: String, title: String){

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.setMessage(message).setTitle(title)

        builder.setPositiveButton("OK") { dialog, which ->

            viewModel.logout(UserPreferences(applicationContext).getToken())

            dialog.dismiss()
        }

        builder.setNegativeButton("CANCEL"){ dialog, wich ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()

        dialog.show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



}


