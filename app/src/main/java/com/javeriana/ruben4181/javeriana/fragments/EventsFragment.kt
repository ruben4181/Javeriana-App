package com.javeriana.ruben4181.javeriana.fragments

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.javeriana.ruben4181.javeriana.HomeActivity
import com.javeriana.ruben4181.javeriana.models.Event
import com.javeriana.ruben4181.javeriana.services.EventsService
import org.json.JSONException
import org.json.JSONObject

class EventsFragment : Fragment() {
    var eventsFragment : List<Event>? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dispWith = this.arguments?.getInt("DISPLAY_WIDTH")
        var width=720
        if(dispWith!=null){
            width=dispWith
        }
        val events = ArrayList<Event>()
    }

    private fun setEvents(events : List<Event>){
        this.eventsFragment=events
    }
    companion object {
        class GetEventsTask internal constructor(val activity : HomeActivity) : AsyncTask<Void, Int, Int>() {
            private val eventsService : EventsService = EventsService()
            private var events : List<Event>? = null
            private lateinit var tmp : HomeActivity

            override fun doInBackground(vararg params: Void?): Int {
                this.tmp=activity
                events = eventsService.getEvents()
                return events!!.size
            }

            override fun onPostExecute(result: Int?) {
                super.onPostExecute(result)
                
            }

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
}