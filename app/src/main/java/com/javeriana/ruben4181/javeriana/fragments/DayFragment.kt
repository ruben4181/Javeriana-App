package com.javeriana.ruben4181.javeriana.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.javeriana.ruben4181.javeriana.R
import com.javeriana.ruben4181.javeriana.adapters.DayAdapter
import com.javeriana.ruben4181.javeriana.services.HorariosDBService

class DayFragment : Fragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var horariosDBService: HorariosDBService
    private lateinit var adapter : DayAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragments_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.days_fragment_recyclerview)
        recyclerView.layoutManager=LinearLayoutManager(view.context, LinearLayout.VERTICAL, false)

        val day = this.arguments?.getString("DAY")
        val date = this.arguments?.getString("DATE")

        horariosDBService= HorariosDBService(this.context!!)
        val dayItems = horariosDBService.getTodayLessons(date!!, day!!)

        adapter = DayAdapter(this.context!!, dayItems)
        recyclerView.adapter=adapter
    }
}