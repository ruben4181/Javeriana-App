package com.javeriana.ruben4181.javeriana

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    private lateinit var logoImageView : ImageView
    private lateinit var usernameTextView : TextView
    private lateinit var passwordTextView : TextView
    private lateinit var loginButton : TextView
    private lateinit var forgotPasswordTextView: TextView

    private val animationDuration : Long = 1100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logoImageView = findViewById(R.id.logo_login)
        usernameTextView = findViewById(R.id.username_login)
        passwordTextView = findViewById(R.id.password_login)
        loginButton = findViewById(R.id.sign_in_login)
        forgotPasswordTextView = findViewById(R.id.forgot_password_login)
    }

    fun login(view: View){
        val intent = Intent(this, HomeActivity::class.java)
        this.startActivity(intent)
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

}
