package com.example.capstoneapp.taxi.ui.screens.practice

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R

@SuppressLint("ClickableViewAccessibility")
@Composable
fun TaxiMain(navController: NavController) {
    val context = LocalContext.current

    AndroidView(
        factory = { inflaterContext ->
            val inflater = LayoutInflater.from(inflaterContext)
            inflater.inflate(R.layout.taxi_first, null).apply {
                val imageView1 = findViewById<ImageView>(R.id.imageView)
                val imageView2 = findViewById<ImageView>(R.id.imageView2)
                val imageButton = findViewById<ImageButton>(R.id.imageButton)
                val textView2 = findViewById<TextView>(R.id.textView2)
                val textView6 = findViewById<TextView>(R.id.textView6)

                // Ensure the ImageView is visible and check the drawable resource
                imageView1.setImageResource(R.drawable.kyonggi_map)
                imageView1.isVisible = true

                imageButton.setOnClickListener {
                    imageView2.setImageResource(R.drawable.marker)
                    imageView2.isVisible = true
                    textView2.text = "현위치 : 경기대학교"
                }

                imageView1.setOnTouchListener { _, event ->
                    if (event.action == MotionEvent.ACTION_MOVE) {
                        val params = imageView2.layoutParams as ConstraintLayout.LayoutParams
                        params.rightMargin -= 10
                        params.bottomMargin += 15
                        imageView2.layoutParams = params
                    }
                    true
                }

                textView6.setOnClickListener {
                   //val intent = Intent(context, SubActivity::class.java)
                //   context.startActivity(intent)
                   navController.navigate("taxi_sub_screen")
                }
            }
        },
        update = { }
    )
}

@Preview(showBackground = true)
@Composable
fun TaxiMainPreview() {
    val navController = rememberNavController()
    TaxiMain(navController)
}
