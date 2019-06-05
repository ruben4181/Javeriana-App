package com.javeriana.ruben4181.javeriana.services

import android.content.Context
import android.widget.Toast
import com.javeriana.ruben4181.javeriana.models.User
import org.json.JSONException
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class UserService(val context : Context) : UserServiceAPI{
    private lateinit var userDBService: UserDBService

    override fun logginUser(username : String, password : String) : User {
        userDBService = UserDBService(context)
        var user=User("", "", "", "", "", "null", "", listOf())
        if(userDBService.verifyUser(username)){
            user=userDBService.getUser(username)
        }else{
            user=verifyUserLoggin(username, password)
            if(!"null".equals(user.token)){
                saveUser(user, username)
            }
        }
        return user
    }

    override fun verifyUserLoggin(username: String, password: String) : User {
        var user = User("", "", "", "", "", "null", "", listOf(""))
        val connection = URL("http://replica.javerianacali.edu.co:8100/WSMobile/mobile/v2/Autenticacion/?usu="+username+
                "&pass="+password).openConnection() as HttpURLConnection
        val data=connection.inputStream.bufferedReader().readText()
        val jsonData=parseJSON(data)
        var valido=false
        if (jsonData!=null){
            valido=jsonData!!.getBoolean("valido")
        }
        if(valido){
            user.nombre=jsonData!!.getString("nombre")
            user.apellido=jsonData!!.getString("apellido")
            user.email=jsonData!!.getString("email")
            user.periodo=jsonData!!.getString("periodo")
            user.emplid=jsonData!!.getString("emplid")
            user.token=jsonData!!.getString("x-t6519fdd1s5q")
            user.token_expire_in=jsonData!!.getString("token_expire_in")
            val roles=ArrayList<String>()
            val jsonRoles = jsonData.getJSONArray("roles")
            val lenghtRoles=jsonRoles.length()
            //for(i in 0..lenghtRoles){
            //    roles.add(jsonRoles[i] as String)
            //}
            user.roles=roles
        }
        return user
    }

    override fun saveUser(user: User, username : String) : Boolean {
        val ans = false
        if(!userDBService.verifyUser(username)){
            userDBService.saveUserLocal(user, username)
        }
        return ans
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