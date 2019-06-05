package com.javeriana.ruben4181.javeriana.fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.javeriana.ruben4181.javeriana.R
import com.javeriana.ruben4181.javeriana.adapters.PagerAdapter
import java.text.SimpleDateFormat
import java.util.*

class ScheduleFragment : Fragment(){
    private var fragments = ArrayList<DayFragment>()
    private var weekSundayDate : String? = ""
    private var currentItem=0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.schedule_main_frame, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pagerAdapter = PagerAdapter(this.childFragmentManager)

        weekSundayDate = this.arguments?.getString("WEEK_START_DATE")
        val thisDay = Calendar.getInstance()

        for(i in 0..6){
            val currentTime = Calendar.getInstance()
            val df = SimpleDateFormat("yyyy-MM-dd")
            currentTime.time=df.parse(weekSundayDate)
            currentTime.add(Calendar.DATE, i)

            val fdate = df.format(currentTime.time)

            if(currentTime.get(Calendar.DAY_OF_WEEK)==thisDay.get(Calendar.DAY_OF_WEEK)){
                currentItem=i
            }

            val fragment = DayFragment()
            val bundle = Bundle()
            bundle.putString("DAY", i.toString()+" ")
            bundle.putString("DATE",fdate)
            fragment.arguments=bundle
            fragments.add(fragment)
        }

        if(currentItem>0){
            currentItem-=1
        }

        pagerAdapter.addFragment(fragments[1], "Lunes")
        pagerAdapter.addFragment(fragments[2], "Martes")
        pagerAdapter.addFragment(fragments[3], "Miercoles")
        pagerAdapter.addFragment(fragments[4], "Jueves")
        pagerAdapter.addFragment(fragments[5], "Viernes")
        pagerAdapter.addFragment(fragments[6], "Sabado")
//      */
        val viewPager = view.findViewById<ViewPager>(R.id.home_viewpager)
        viewPager.adapter=pagerAdapter
        viewPager.currentItem=currentItem
        val tabLayout = view.findViewById<TabLayout>(R.id.tabs)
        tabLayout.tabMode=TabLayout.MODE_SCROLLABLE
        tabLayout.setupWithViewPager(viewPager)
    }
}