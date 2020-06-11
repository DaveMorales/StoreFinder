package com.fdmt.walmart.presentation

import android.os.Bundle
import com.fdmt.walmart.presentation.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun getNavControllerId(): Int {
        return R.id.nav_host_fragment
    }

}