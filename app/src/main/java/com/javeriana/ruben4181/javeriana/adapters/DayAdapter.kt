package com.javeriana.ruben4181.javeriana.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.javeriana.ruben4181.javeriana.R
import com.javeriana.ruben4181.javeriana.models.DayItem
import com.javeriana.ruben4181.javeriana.services.HorariosDBService

class DayAdapter(val context : Context,val items : List<DayItem>) : RecyclerView.Adapter<DayAdapter.ViewHolder>() {
    private val data = items

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.horaInicio.text=data[p1].horarios.hora.take(5)
        p0.horaFin.text=data[p1].horarios.hora.substring(data[p1].horarios.hora.length-5, data[p1].horarios.hora.length)
        p0.subject.text=data[p1].nombre
        p0.salon.text=data[p1].horarios.saln
        p0.teacher.text=data[p1].horarios.doc
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.class_card_view, p0, false)
        return DayAdapter.ViewHolder(v)
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val horaInicio = itemView.findViewById<TextView>(R.id.horario_list_hi)
        val horaFin = itemView.findViewById<TextView>(R.id.horario_list_hf)
        val subject = itemView.findViewById<TextView>(R.id.horario_list_subject)
        val salon = itemView.findViewById<TextView>(R.id.horario_list_salon)
        val teacher = itemView.findViewById<TextView>(R.id.horario_list_teacher)
    }
}