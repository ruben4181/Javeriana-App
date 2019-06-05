package com.javeriana.ruben4181.javeriana.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
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
import com.javeriana.ruben4181.javeriana.adapters.SubjectsAdapter
import com.javeriana.ruben4181.javeriana.models.Subject
import com.javeriana.ruben4181.javeriana.models.User
import com.javeriana.ruben4181.javeriana.services.SubjectsService
import com.javeriana.ruben4181.javeriana.services.UserDBService
import kotlinx.android.synthetic.main.fragment_subjects.view.*

class SubjectsFragment() : Fragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var userDBService: UserDBService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subjects, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.subjects_fragment_recycler_view)
        recyclerView.layoutManager= LinearLayoutManager(view.context, LinearLayout.VERTICAL, false)
        userDBService = UserDBService(context!!)
        val adapter = SubjectsAdapter(ArrayList<Subject>())
        val user = userDBService.getUser()
        recyclerView.adapter=adapter

        if(isConnected()){
            val myTask = GetSubjectsTask(context!!, adapter)
            myTask.execute(user)
        }else{
            Toast.makeText(context, "Revisa tu conexion a internet", Toast.LENGTH_SHORT).show()
        }
    }

    fun isConnected() : Boolean{
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

    companion object {
        class GetSubjectsTask internal constructor(val context: Context, val adapter : SubjectsAdapter) : AsyncTask<User, Int, List<Subject>>() {

            private val subjectsService = SubjectsService(context)

            override fun doInBackground(vararg params: User?): List<Subject>? {
                val subs=subjectsService.getSubjects(params[0]!!)
                return subs
            }

            override fun onPostExecute(result: List<Subject>?) {
                super.onPostExecute(result)
                adapter.updateSubjects(result!!)
                adapter.notifyDataSetChanged()
            }
        }
    }
}