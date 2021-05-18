package br.com.lucad.kotlinmarmitex.ui.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.lucad.kotlinmarmitex.R

class SplashActivity : AppCompatActivity() {

    lateinit var foodIcon: ImageView
    private lateinit var constraintSplash: ConstraintLayout
    lateinit var textViewName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        foodIcon = findViewById(R.id.image_splash)
        constraintSplash = findViewById(R.id.constraint_splash)
        textViewName = findViewById(R.id.text_view_splash_name)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        rotateFoodIcon()

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000) //
    }

    private fun rotateFoodIcon(){

        val colorFrom = Color.WHITE
        val colorTo = Color.RED
        val duration: Long = 2000
        ObjectAnimator.ofObject(
            constraintSplash,
            "backgroundColor",
            ArgbEvaluator(),
            colorFrom,
            colorTo
        )
            .setDuration(duration)
            .start()

        val animator = ObjectAnimator.ofFloat(foodIcon, View.ROTATION, -360f, 0f)
        animator.duration = 1000
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                textViewName.visibility = View.VISIBLE
            }
        })
        animator.start()
    }
}