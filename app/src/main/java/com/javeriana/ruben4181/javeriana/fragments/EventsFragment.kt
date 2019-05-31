package com.javeriana.ruben4181.javeriana.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.javeriana.ruben4181.javeriana.HomeActivity
import com.javeriana.ruben4181.javeriana.R
import com.javeriana.ruben4181.javeriana.adapters.EventsAdapter
import com.javeriana.ruben4181.javeriana.models.Event
import com.javeriana.ruben4181.javeriana.models.Schedule
import com.javeriana.ruben4181.javeriana.models.Subject
import com.javeriana.ruben4181.javeriana.models.User
import com.javeriana.ruben4181.javeriana.services.EventsService
import com.javeriana.ruben4181.javeriana.services.HorariosDBService
import com.javeriana.ruben4181.javeriana.services.SubjectsService
import com.javeriana.ruben4181.javeriana.services.UserDBService
import kotlinx.android.synthetic.main.fragment_events.*
import org.json.JSONException
import org.json.JSONObject

class EventsFragment : Fragment() {
    private lateinit var recyclerView : RecyclerView
    private lateinit var usersDBService : UserDBService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dispWith = this.arguments?.getInt("DISPLAY_WIDTH")
        var width=720
        if(dispWith!=null) {
            width = dispWith
        }
        recyclerView=view.findViewById<RecyclerView>(R.id.events_fragment_recycler_view)

        recyclerView.layoutManager= LinearLayoutManager(view.context, LinearLayout.VERTICAL, false) as RecyclerView.LayoutManager?
        val listEvents = ArrayList<Event>()
        val eventsAdapter = EventsAdapter(listEvents as List<Event>, dispWith!!, this.context!!)
        recyclerView.adapter=eventsAdapter

        if(this.isConnected()) {
            val myTask = GetEventsTask(eventsAdapter)
            myTask.execute()
            usersDBService = UserDBService(this.context!!)
            val user = usersDBService.getUser()
            val mySecondTask = UpdateSchedule(this.context!!)
            mySecondTask.execute(user.token)
        }else{
            Toast.makeText(context, "Lo sentimos, debes estar conectado para usar esta app", Toast.LENGTH_SHORT).show()
        }
    }

    fun isConnected() : Boolean{
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }
    companion object {
        class GetEventsTask internal constructor(var eventsAdapter: EventsAdapter) : AsyncTask<Void, Int, Int>() {
            private val eventsService : EventsService = EventsService()
            private var events : List<Event>? = null
            private lateinit var tmp : HomeActivity

            override fun doInBackground(vararg params: Void?): Int {
                events = eventsService.getEvents()
                return 0
            }

            override fun onPostExecute(result: Int?) {
                super.onPostExecute(result)
                eventsAdapter.updateEvents(events!!)
                eventsAdapter.notifyDataSetChanged()
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
        class UpdateSchedule internal constructor(val context: Context) : AsyncTask<String, Void, Int>(){
            private val horariosDBService = HorariosDBService(context)
            private val subjectsService = SubjectsService(context)
            private var subjects : List<Subject>? = ArrayList<Subject>()
            override fun doInBackground(vararg params: String?) : Int{
                horariosDBService.clearDB()
                subjects=subjectsService.getSubjects(User("", "", "","", "", params[0]!!, "", listOf()))
                return 1
            }

            override fun onPostExecute(result: Int?) {
                super.onPostExecute(result)
                for(s in subjects!!){
                    val horarios = ArrayList<Schedule>()
                    for(h in s.horario){
                        horarios.add(h)
                        //Log.e("CLEAR", h.fecf+" "+h.feci+" "+h.doc)
                    }
                    horariosDBService.insertSchedule(s.codigo, s.nombre, horarios)
                }
            }
        }
    }
}