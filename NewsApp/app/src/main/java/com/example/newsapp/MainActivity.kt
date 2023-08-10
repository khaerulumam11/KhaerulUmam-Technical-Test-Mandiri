package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newsapp.adapter.PagerAdapter
import com.example.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var pagerAdapter:PagerAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = "News App"
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))
        pagerAdapter = PagerAdapter(supportFragmentManager)
        binding.viewpagerMain.adapter = pagerAdapter
        binding.tabsMain.setupWithViewPager(binding.viewpagerMain)
    }
}