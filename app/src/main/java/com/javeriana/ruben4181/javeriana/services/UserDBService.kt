package com.javeriana.ruben4181.javeriana.services

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.javeriana.ruben4181.javeriana.models.User

class UserDBService(context : Context) : SQLiteOpenHelper(context, "UserDBServices", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        val query="CREATE TABLE users(username text primary key, nombre text, apellido text, email text, "+
                "periodo text, emplid text, token text, token_expire_in text, roles text)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun verifyUser(username : String) : Boolean{
        val query="SELECT token FROM users WHERE username='${username}'"
        val result : Cursor = this.executeQuery(query, this.readableDatabase)
        var ans=false
        if (result.moveToFirst()){
            ans=true
        }
        return ans
    }

    fun getUser(username: String) : User{
        val user=User("", "", "", "", "","", "", listOf(""))
        if(verifyUser(username)){
            val query="SELECT nombre, apellido, email, periodo, emplid, token, token_expire_in, roles " +
                    "FROM users WHERE username='${username}'"
            val result : Cursor = this.executeQuery(query, this.readableDatabase)
            if(result.moveToFirst()){
                user.nombre=result.getString(0)
                user.apellido=result.getString(1)
                user.email=result.getString(2)
                user.periodo=result.getString(3)
                user.emplid=result.getString(4)
                user.token=result.getString(5)
                user.token_expire_in=result.getString(6)
                user.roles=result.getString(7).split("-")
            }
        }
        return user
    }

    fun getUser() : User{
        val user=User("", "", "", "", "","", "", listOf(""))

        val query="SELECT nombre, apellido, email, periodo, emplid, token, token_expire_in, roles " +
                "FROM users"
        val result : Cursor = this.executeQuery(query, this.readableDatabase)
        if(result.moveToFirst()){
            user.nombre=result.getString(0)
            user.apellido=result.getString(1)
            user.email=result.getString(2)
            user.periodo=result.getString(3)
            user.emplid=result.getString(4)
            user.token=result.getString(5)
            user.token_expire_in=result.getString(6)
            user.roles=result.getString(7).split("-")

        }
        return user
    }

    fun clearDB(){
        this.writableDatabase.execSQL("DELETE from users")
    }

    fun saveUserLocal(user : User, username : String){
        clearDB()

        var local = ContentValues()
        local.put("username", username)
        local.put("nombre", user.nombre)
        local.put("apellido", user.apellido)
        local.put("email", user.email)
        local.put("periodo", user.periodo)
        local.put("emplid", user.emplid)
        local.put("token", user.token)
        local.put("token_expire_in", user.token_expire_in)
        var tmp=""
        for(role in user.roles){
            tmp+=role+"-"
        }
        if(tmp.length>0) {
            local.put("roles", tmp.take(tmp.length - 1))
        }else{
            local.put("roles", "")
        }
        this.executeModification(local)
    }

    private fun executeQuery(sql: String, bd : SQLiteDatabase) : Cursor
    {
        val consulta : Cursor = bd.rawQuery(sql,null)
        return consulta
    }

    private fun executeModification(user : ContentValues){
        val bd = this.writableDatabase
        bd.insert("users", null, user)
        bd.close()
    }
    fun isUserLogged() : Boolean{
        val query="SELECT username FROM users"
        val result = this.executeQuery(query, this.readableDatabase)
        var ans=false
        if(result.moveToFirst()){
            ans=true
        }
        return ans
    }
}