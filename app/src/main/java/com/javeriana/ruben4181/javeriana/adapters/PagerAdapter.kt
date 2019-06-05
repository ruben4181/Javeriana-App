package com.javeriana.ruben4181.javeriana.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class PagerAdapter(manager : FragmentManager) : FragmentPagerAdapter(manager){
    private val fragmentList : MutableList<Fragment> = ArrayList()
    private val titles : MutableList<String> = ArrayList()
    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

    fun addFragment(fragment: Fragment, title : String) {
        fragmentList.add(fragment)
        titles.add(title)
    }
}