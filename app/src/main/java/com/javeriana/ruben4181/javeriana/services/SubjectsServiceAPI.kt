package com.javeriana.ruben4181.javeriana.services

import com.javeriana.ruben4181.javeriana.models.Subject
import com.javeriana.ruben4181.javeriana.models.User

interface SubjectsServiceAPI{
    fun getSubjects(user : User) : List<Subject>?
    fun getSubject(codigo : String) : Subject?
}