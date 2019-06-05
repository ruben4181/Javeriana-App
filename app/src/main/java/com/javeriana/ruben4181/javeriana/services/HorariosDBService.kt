package com.javeriana.ruben4181.javeriana.services

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.javeriana.ruben4181.javeriana.models.DayItem
import com.javeriana.ruben4181.javeriana.models.Schedule

class HorariosDBService(context : Context) : SQLiteOpenHelper(context, "horariosDB", null, 1){
    private lateinit var dbTmp : SQLiteDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        val query="CREATE TABLE horarios(codigo text, asignatura_name text, starts text, ends text, time text, salon text, profesor text," +
                "dia text)"
        db?.execSQL(query)
    }

    fun clearDB(){
        this.writableDatabase.execSQL("DELETE from horarios")
    }

    fun insertSchedule(code : String, subject: String, horario : List<Schedule>){
        for(h in horario){
            val localHorario = ContentValues()
            localHorario.put("codigo", code)
            localHorario.put("asignatura_name", subject)
            localHorario.put("starts", h.feci)
            localHorario.put("ends", h.fecf)
            localHorario.put("time", h.hora)
            localHorario.put("salon", h.saln)
            localHorario.put("profesor", h.doc)
            localHorario.put("dia", h.dia)
            this.executeModification(localHorario)
        }
    }

    fun getTodayLessons(date : String, day : String) : List<DayItem>{
        val query="SELECT codigo, asignatura_name, starts, time, salon, ends, profesor, dia FROM horarios WHERE dia='"+day+"'"+
                " AND DATE('${date}')>=DATE(starts) AND DATE('${date}')<=DATE(ends)"//+" AND starts>=DATE(${date}) AND ends<=DATE(${date})"
        val result : Cursor = this.executeQuery(query, this.readableDatabase)
        val todayLessons = ArrayList<DayItem>()
        if(result.moveToFirst()){
            val dayItem = DayItem(result.getString(0),
                result.getString(1),
                Schedule(result.getString(2), result.getString(3), result.getString(4),
                    result.getString(5), result.getString(6), result.getString(7))
            )
            todayLessons.add(dayItem)
        }
        while(result.moveToNext()){
            var teacher = ""
            if(result.getString(6)!=null){
                teacher=result.getString(6)
            }
            val dayItem = DayItem(result.getString(0),
                result.getString(1),
                Schedule(result.getString(2), result.getString(3), result.getString(4),
                    result.getString(5), teacher, result.getString(7))
            )
            todayLessons.add(dayItem)
        }
        return sortTodayLessons(todayLessons)
    }

    private fun sortTodayLessons(lessons : List<DayItem>): List<DayItem>{
        val values=ArrayList<Pair<Int, Int>>()
        val sortedLessons = ArrayList<DayItem>()
        for(i in 0..lessons.size-1){
            val init=lessons[i].horarios.hora.substring(0, 2)+lessons[i].horarios.hora.substring(3, 5)
            val intValue = init.toInt()
            val pair=Pair(intValue, i)
            values.add(pair)
        }
        values.sortBy { it.first }
        for(i in 0..values.size-1){
            sortedLessons.add(lessons[values[i].second])
        }
        return sortedLessons
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun executeQuery(sql: String, bd : SQLiteDatabase) : Cursor
    {
        val consulta : Cursor = bd.rawQuery(sql,null)
        return consulta
    }

    private fun executeModification(user : ContentValues){
        val bd = this.writableDatabase
        bd.insert("horarios", null, user)
        bd.close()
    }

}