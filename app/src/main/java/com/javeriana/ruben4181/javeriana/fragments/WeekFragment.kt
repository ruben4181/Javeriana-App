package com.javeriana.ruben4181.javeriana.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
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

class WeekFragment : Fragment(){
    private var firstDay : String? = ""
    private var currentIndex = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.week_mainframe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pagerAdapter = PagerAdapter(this.childFragmentManager)
        val df = SimpleDateFormat("yyyy-MM-dd")

        if(!isConnected()){
            Toast.makeText(this.context!!, "No estas conectado, si ha ocurrido un cambio" +
                    " en tu horario, no lo podras ver", Toast.LENGTH_LONG).show()
        }

        firstDay=this.arguments?.getString("FIRST_DAY_DATE")
        val thisWeek=Calendar.getInstance()
        thisWeek.add(Calendar.DAY_OF_WEEK, -(thisWeek.get(Calendar.DAY_OF_WEEK)-1))
        Log.e("THISWEEK", df.format(thisWeek.time))
        for(i in 0..19){
            val c = Calendar.getInstance()
            c.time=df.parse(firstDay)
            c.add(Calendar.DATE, i*7)

            if(df.format(thisWeek.time).equals(df.format(c.time))){
                currentIndex=i
            }

            val sFragment = ScheduleFragment()
            val bundle = Bundle()
            bundle.putString("WEEK_START_DATE", df.format(c.time))
            sFragment.arguments=bundle
            pagerAdapter.addFragment(sFragment, "Semana "+(i+1).toString())
        }

        val viewPager = view.findViewById<ViewPager>(R.id.home_viewpager_week)
        viewPager.adapter=pagerAdapter

        viewPager.currentItem=currentIndex

        val tabLayout = view.findViewById<TabLayout>(R.id.tabs_week)
        tabLayout.tabMode=TabLayout.MODE_SCROLLABLE
        tabLayout.setupWithViewPager(viewPager)

    }

    fun isConnected() : Boolean{
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

}