package com.javeriana.ruben4181.javeriana.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.javeriana.ruben4181.javeriana.EventViewer
import com.javeriana.ruben4181.javeriana.R
import com.javeriana.ruben4181.javeriana.models.Event
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class EventsAdapter(val data : List<Event>, val dispWidth : Int, val context : Context): RecyclerView.Adapter<EventsAdapter.ViewHolder>(){
    private var items : List<Event> = data

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): EventsAdapter.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.event_card_view, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: EventsAdapter.ViewHolder, p1: Int) {
        p0.titleTV.text = Html.fromHtml(Html.fromHtml(items[p1].title).toString())
        if (items[p1].body.length>400) {
            p0.bodyTV.text = Html.fromHtml(Html.fromHtml(items[p1].body.take(400) + "...").toString())
        }else{
            p0.bodyTV.text = Html.fromHtml(Html.fromHtml(items[p1].body).toString())
        }
        p0.posdateTV.text = Html.fromHtml(Html.fromHtml("Publicado: "+items[p1].Publicado).toString())
        if (items[p1].field_image_box != "") {
            Picasso.get().load(items[p1].field_image_box).resize(dispWidth, p0.eventImage.maxHeight).centerCrop().into(p0.eventImage)
            //Picasso.get().load(items[p1].field_image_box).fit().centerInside().into(p0.eventImage)
        }
        1
        p0.viewMoreButton.setOnClickListener() { v ->
            val i = Intent(context, EventViewer::class.java)
            i.putExtra("nid", items[p1].nid)
            context.startActivity(i)
        }
    }
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val titleTV = itemView.findViewById<TextView>(R.id.event_title)
        val posdateTV = itemView.findViewById<TextView>(R.id.event_postdate)
        val bodyTV = itemView.findViewById<TextView>(R.id.event_body)
        val eventImage = itemView.findViewById<ImageView>(R.id.event_imageview)
        val viewMoreButton = itemView.findViewById<Button>(R.id.event_viewmore)
    }

    fun updateEvents(newData : List<Event>){
        this.items=newData
    }
}