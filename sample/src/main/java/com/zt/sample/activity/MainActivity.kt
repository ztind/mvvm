package com.zt.sample.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zt.mvvm.view.BaseActivity
import com.zt.sample.R
import com.zt.sample.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel,ActivityMainBinding>() {
    override fun setLayoutResId(): Int = R.layout.activity_main
}