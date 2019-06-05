package com.javeriana.ruben4181.javeriana

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import com.javeriana.ruben4181.javeriana.adapters.EventsAdapter
import com.javeriana.ruben4181.javeriana.models.Event
import com.javeriana.ruben4181.javeriana.services.EventsService
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class EventViewer : AppCompatActivity(){
    private val eventsService = EventsService()
    private lateinit var eventImageView : ImageView
    private lateinit var title : TextView
    private lateinit var postdate : TextView
    private lateinit var body : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_viewer)
        eventImageView = findViewById(R.id.event_viewer_imageview)
        title=findViewById(R.id.event_viewer_title)
        postdate=findViewById(R.id.event_viewer_postdate)
        body=findViewById(R.id.event_viewer_body)


        val extras = this.intent.extras
        val nid = extras.getString("nid")

        val myTask = GetEventTask(nid, eventImageView, title, postdate, body)
        myTask.execute()
    }
    class GetEventTask internal constructor(val nid : String, val image : ImageView, val title : TextView, val postDate : TextView, val body : TextView)
        : AsyncTask<Void, Int, Int>() {

        private val eventsService : EventsService = EventsService()
        private var event : Event? = null
        private lateinit var tmp : HomeActivity

        override fun doInBackground(vararg params: Void?): Int {
            event = eventsService.getEvent(nid)
            return 1
        }

        override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)
            title.text= Html.fromHtml(Html.fromHtml(event!!.title).toString())
            postDate.text = Html.fromHtml(Html.fromHtml(event!!.Publicado).toString())
            body.text=Html.fromHtml(Html.fromHtml(event!!.body).toString())
            Picasso.get().load(event!!.field_image_box).fit().centerInside().into(image)
        }

    }
    companion object {

    }

}