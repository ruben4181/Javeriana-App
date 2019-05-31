package com.javeriana.ruben4181.javeriana

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Html
import android.view.Display
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.javeriana.ruben4181.javeriana.fragments.EventsFragment
import com.javeriana.ruben4181.javeriana.fragments.ScheduleFragment
import com.javeriana.ruben4181.javeriana.fragments.SubjectsFragment
import com.javeriana.ruben4181.javeriana.fragments.WeekFragment
import com.javeriana.ruben4181.javeriana.models.Event
import com.javeriana.ruben4181.javeriana.models.Subject
import com.javeriana.ruben4181.javeriana.models.User
import com.javeriana.ruben4181.javeriana.services.EventsService
import com.javeriana.ruben4181.javeriana.services.SubjectsService
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class HomeActivity : AppCompatActivity(){
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var display : Display

    private lateinit var ft : FragmentTransaction
    private lateinit var fragment : Fragment
    private val listFragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        val toolbar : Toolbar = findViewById(R.id.toolbar)
        this.setSupportActionBar(toolbar)
        val actionBar : ActionBar? = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)
        }

        drawerLayout = findViewById(R.id.drawer_layout)

        val navigationView : NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true

            displaySelectedScreen(menuItem.itemId)

            drawerLayout.closeDrawers()
            true
        }

        display = this.windowManager.defaultDisplay

        val bundle = Bundle()
        bundle.putInt("DISPLAY_WIDTH", display.width)
        fragment = EventsFragment()
        fragment.arguments=bundle

        listFragments.add(fragment)
        listFragments.add(SubjectsFragment())
        //listFragments.add(ScheduleFragment())
        val weekFragment = WeekFragment()
        val bundle2= Bundle()
        bundle2.putString("FIRST_DAY_DATE", "2019-01-20")
        weekFragment.arguments=bundle2


        listFragments.add(weekFragment)


        ft=supportFragmentManager.beginTransaction()
        ft.replace(R.id.content_frame, fragment)
        ft.disallowAddToBackStack()
        ft.commit()

    }

    fun displaySelectedScreen(itemId : Int){
        if(itemId==R.id.nav_menu1){
            fragment=listFragments[0]
            val bundle=Bundle()
            bundle.putInt("DISPLAY_WIDTH", display.width)
            fragment.arguments=bundle
        }
        if(itemId==R.id.nav_menu2){
            fragment=listFragments[1]
        }

        if(itemId==R.id.nav_menu3){
            fragment=listFragments[2]
        }

        if(fragment!=null){
            ft=supportFragmentManager.beginTransaction()
            ft.replace(R.id.content_frame, fragment)
            ft.commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}