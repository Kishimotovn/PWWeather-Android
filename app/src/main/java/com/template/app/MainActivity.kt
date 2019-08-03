package com.template.app

import android.os.Bundle
import android.view.LayoutInflater
import com.template.app.base.BaseActivity
import com.template.app.databinding.ActivityMainBinding
import dagger.android.AndroidInjection


class MainActivity: BaseActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
//        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        this.setContentView(this.binding.root)
        this.getComponent().inject(this)
    }
}