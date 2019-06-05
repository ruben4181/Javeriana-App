package com.javeriana.ruben4181.javeriana.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.javeriana.ruben4181.javeriana.R
import com.javeriana.ruben4181.javeriana.models.Subject
import kotlinx.android.synthetic.main.subject_card_view.view.*

class SubjectsAdapter(val data : List<Subject>) : RecyclerView.Adapter<SubjectsAdapter.ViewHolder>(){
    private var items = data

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.subject_card_view, p0, false)
        return SubjectsAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.subjectName.text=items[p1].nombre
        val teachers = ArrayList<String>()
        var tmp=""
        for(h in items[p1].horario){
            if(!isInArray(h.doc, teachers)){
                teachers.add(h.doc)
                tmp+=h.doc+", "
            }
        }
        p0.subjectTeacher.text=tmp.take(tmp.length-2)
        p0.subjectCode.text=items[p1].codigo
        p0.subjectQualification.text="Nota: "+items[p1].notp.take(3)+"\n"
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val subjectName = itemView.findViewById<TextView>(R.id.subject_list_name)
        val subjectTeacher = itemView.findViewById<TextView>(R.id.subject_list_teacher)
        val subjectCode = itemView.findViewById<TextView>(R.id.subject_list_code)
        val subjectQualification = itemView.findViewById<TextView>(R.id.subject_nota_cardview)
    }

    private fun isInArray(value : String, items : List<String>) : Boolean{
        for(item in items){
            if(value==item){
                return true
            }
        }
        return false
    }

    fun updateSubjects(subjects : List<Subject>){
        this.items=subjects
    }
}