package com.example.newsapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.newsapp.fragment.business.BusinessFragment
import com.example.newsapp.fragment.entertainment.EntertainmentFragment
import com.example.newsapp.fragment.general.GeneralFragment
import com.example.newsapp.fragment.health.HealthFragment
import com.example.newsapp.fragment.science.ScienceFragment
import com.example.newsapp.fragment.sports.SportsFragment
import com.example.newsapp.fragment.technology.TechnologyFragment

class PagerAdapter (fm: FragmentManager): FragmentPagerAdapter(fm) {

    // sebuah list yang menampung objek Fragment
    private val pages = listOf(
        GeneralFragment(),
        EntertainmentFragment(),
        BusinessFragment(),
        ScienceFragment(),
        HealthFragment(),
        SportsFragment(),
        TechnologyFragment()
    )

    // menentukan fragment yang akan dibuka pada posisi tertentu
    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    // judul untuk tabs
    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "General"
            1 -> "Entertainment"
            2 -> "Business"
            3 -> "Science"
            4 -> "Health"
            5 -> "Sports"
            else -> "Technology"
        }
    }
}