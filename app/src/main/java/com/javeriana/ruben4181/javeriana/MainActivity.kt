package com.javeriana.ruben4181.javeriana

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    private lateinit var logoImageView : ImageView
    private val animationDuration : Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logoImageView = findViewById(R.id.logo_login)
    }

    fun initialAnimation(){
        val pvhY = PropertyValuesHolder.ofFloat("y", 30f)
        val pvhA = PropertyValuesHolder.ofFloat(View.ALPHA, 0.0f, 1.0f)
        val allAnimation = ObjectAnimator.ofPropertyValuesHolder(logoImageView, pvhY, pvhA)
        val animatorY = ObjectAnimator.ofFloat(logoImageView, "y", 30f)
        animatorY.setDuration(animationDuration)
        val animatorSet = AnimatorSet()
        val alphaAnimator = ObjectAnimator.ofFloat(logoImageView, View.ALPHA, 0.0f, 1.0f)
        alphaAnimator.setDuration(animationDuration)
        val anim =
        animatorSet.play(allAnimation)
        animatorSet.start()
    }

    fun testAnimation(view : View){
        initialAnimation()
    }
}
