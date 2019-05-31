package com.javeriana.ruben4181.javeriana

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.javeriana.ruben4181.javeriana.models.Event
import com.javeriana.ruben4181.javeriana.models.User
import com.javeriana.ruben4181.javeriana.services.EventsService
import com.javeriana.ruben4181.javeriana.services.UserDBService
import com.javeriana.ruben4181.javeriana.services.UserService
import com.squareup.picasso.Picasso

class LoginActivity : AppCompatActivity() {
    private lateinit var logoImageView : ImageView
    private lateinit var usernameTextView : TextView
    private lateinit var passwordTextView : TextView
    private lateinit var loginButton : TextView
    private lateinit var forgotPasswordTextView: TextView
    private lateinit var userService: UserService
    private lateinit var userDBService: UserDBService

    private val animationDuration : Long = 1100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logoImageView = findViewById(R.id.logo_login)
        usernameTextView = findViewById(R.id.username_login)
        passwordTextView = findViewById(R.id.password_login)
        loginButton = findViewById(R.id.sign_in_login)
        forgotPasswordTextView = findViewById(R.id.forgot_password_login)
        userService= UserService(this)
        userDBService= UserDBService(this)
        if(userDBService.isUserLogged()){
            Toast.makeText(this, "Hola de nuevo!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }
    }

    fun login(view: View){
        val loginTask = LoginTask(this, this)
        loginTask.execute(usernameTextView.text.toString(), passwordTextView.text.toString())
    }

    override fun onPostResume() {
        super.onPostResume()
        initialAnimation()
    }

    fun initialAnimation(){
        val pvhY = PropertyValuesHolder.ofFloat("y", convertDpToPx(this, 150f))
        val pvhA = PropertyValuesHolder.ofFloat(View.ALPHA, 0.0f, 1.0f)
        val allAnimation = ObjectAnimator.ofPropertyValuesHolder(logoImageView, pvhY, pvhA)
        allAnimation.duration=animationDuration

        val userNamepvhY = PropertyValuesHolder.ofFloat("y", convertDpToPx(this, 360f))
        val userNamepvhA = PropertyValuesHolder.ofFloat(View.ALPHA, 0.0f, 1.0f)
        val userNameAnimation = ObjectAnimator.ofPropertyValuesHolder(usernameTextView, userNamepvhY, userNamepvhA)
        userNameAnimation.duration=animationDuration

        val passpvhY = PropertyValuesHolder.ofFloat("y", convertDpToPx(this, 400f))
        val passpvhA = PropertyValuesHolder.ofFloat(View.ALPHA, 0.0f, 1.0f)
        val passAnimation = ObjectAnimator.ofPropertyValuesHolder(passwordTextView, passpvhY, passpvhA)
        passAnimation.duration=animationDuration

        val loginpvhY = PropertyValuesHolder.ofFloat("y", convertDpToPx(this, 460f))
        val loginpvhA = PropertyValuesHolder.ofFloat(View.ALPHA, 0.0f, 1.0f)
        val loginAnimation = ObjectAnimator.ofPropertyValuesHolder(loginButton,loginpvhY, loginpvhA)
        loginAnimation.duration=animationDuration

        val forgotpvhY = PropertyValuesHolder.ofFloat("y", convertDpToPx(this, 520f))
        val forgotpvhA = PropertyValuesHolder.ofFloat(View.ALPHA, 0.0f, 1.0f)
        val forgotAnimation = ObjectAnimator.ofPropertyValuesHolder(forgotPasswordTextView,forgotpvhY, forgotpvhA)
        forgotAnimation.duration=animationDuration

        val animatorSet = AnimatorSet()

        val anim = animatorSet.playTogether(allAnimation, userNameAnimation, passAnimation, loginAnimation, passAnimation, forgotAnimation)
        animatorSet.start()
    }

    fun convertDpToPx(context: Context, dp: Float): Float {
        return dp * context.getResources().getDisplayMetrics().density
    }
    companion object {
        class LoginTask internal constructor(val context : Context, val activity: AppCompatActivity)
            : AsyncTask<String, Int, User>() {

            private val userService : UserService = UserService(context)

            override fun doInBackground(vararg params: String?): User? {
                val user=userService.logginUser(params!![0]!!, params!![1]!!)
                return user
            }

            override fun onPostExecute(result: User?) {
                super.onPostExecute(result)
                if(!"null".equals(result?.token)) {
                    Toast.makeText(context, "Bienvenido, "+result?.nombre, Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, HomeActivity::class.java)
                    context.startActivity(intent)
                    activity.finish()
                }else{
                    Toast.makeText(context, "Lo sentimos, usuario/contraseña indicados no son correctos", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}
