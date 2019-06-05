package com.javeriana.ruben4181.javeriana.services

import com.javeriana.ruben4181.javeriana.models.User

interface UserServiceAPI {
    fun logginUser(username : String, password : String) : User
    fun verifyUserLoggin(username : String, password : String) : User
    fun saveUser(user : User, username: String) : Boolean
}