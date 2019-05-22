package com.javeriana.ruben4181.javeriana.services

import com.google.gson.Gson
import com.javeriana.ruben4181.javeriana.models.Event
import org.json.JSONException
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class EventsService : EventsServiceAPI{
    private val baseURL = "http://replica.javerianacali.edu.co:8100/WSMobile/mobile/v2/"
    private val gson = Gson()
    private val service = "noticias"
    override fun getEvent(): Event? {
        val event = Event("", "", "", "", "", "", "")
        return event
    }

    override fun getEvents(): List<Event>? {
        val connection = URL(baseURL+service+"?page=1&limit=1&filter= ").openConnection() as HttpURLConnection
        val data=connection.inputStream.bufferedReader().readText()
        val jsonData = parseJSON(data)
        val rows = jsonData?.getString("rows")

        val events: List<Event>? = gson.fromJson(rows , Array<Event>::class.java).toList()

        return events
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