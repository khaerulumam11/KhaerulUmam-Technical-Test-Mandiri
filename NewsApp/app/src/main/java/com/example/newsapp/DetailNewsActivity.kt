package com.example.newsapp

import android.os.Bundle
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.databinding.ActivityDetailNewsBinding
import com.example.newsapp.util.WebViewClientImpl


class DetailNewsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = intent.getStringExtra("name")
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_white_24)
        binding.toolbar.setNavigationOnClickListener { finish() }

        val webSettings: WebSettings = binding.webView.getSettings()
        webSettings.javaScriptEnabled = true

        val webViewClient = WebViewClientImpl(this)
        binding.webView.setWebViewClient(webViewClient)

        binding.webView.loadUrl(intent.getStringExtra("url")!!)
        binding.loading.hide()
    }
}