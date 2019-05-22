package com.javeriana.ruben4181.javeriana.services

import com.javeriana.ruben4181.javeriana.models.Event

interface EventsServiceAPI{
    fun getEvents() : List<Event>?
    fun getEvent() : Event?
}