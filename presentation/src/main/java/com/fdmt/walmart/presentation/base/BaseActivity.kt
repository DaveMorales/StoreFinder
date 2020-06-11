package com.fdmt.walmart.presentation.base

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController

abstract class BaseActivity : AppCompatActivity() {

    internal val navController: NavController by lazy {
        findNavController(getNavControllerId())
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    abstract fun getNavControllerId(): Int

}