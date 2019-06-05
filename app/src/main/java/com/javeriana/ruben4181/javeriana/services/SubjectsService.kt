package com.javeriana.ruben4181.javeriana.services

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.javeriana.ruben4181.javeriana.models.Schedule
import com.javeriana.ruben4181.javeriana.models.Subject
import com.javeriana.ruben4181.javeriana.models.User
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

//Supuestamente esto ya trae los datos con el header en cuestion xdddddd

class SubjectsService(val context : Context) : SubjectsServiceAPI{
    private val gson = Gson()
    private val baseURL = "http://replica.javerianacali.edu.co:8100/WSMobile/mobile/v2/"
    private val service="asignaturas"
    override fun getSubjects(user: User): List<Subject>? {
        val ans = ArrayList<Subject>()
        val connection = URL(baseURL+service).openConnection() as HttpURLConnection
        connection.addRequestProperty("x-t6519fdd1s5q", user.token)
        val data=connection.inputStream.bufferedReader().readText()
        val subjectsJSON = JSONArray(data)
        val lenght=subjectsJSON.length()
        for(i in 0..lenght-1){
            val item=subjectsJSON.getJSONObject(i)
            val scheduleData = item.getString("horario")
            val horarios = gson.fromJson(scheduleData, Array<Schedule>::class.java).toList()
            val subject=Subject(item.getString("nom"),
                item.getString("peri"),
                item.getString("coda"),
                item.getString("Strm"),
                item.getString("notp"),
                item.getString("porci"),
                item.getString("class_section"),
                item.getString("crse_id"),
                horarios, listOf(), listOf())
            ans.add(subject)
        }
        return ans
    }

    override fun getSubject(codigo: String): Subject? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    fun parseJSON(json : String) : JSONObject?{
        var jsonObject : JSONObject? = null
        try{
            jsonObject= JSONObject(json)
        }catch (e : JSONException){
            e.printStackTrace()
        }
        return jsonObject
    }
}