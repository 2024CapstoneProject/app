package com.example.capstoneapp.Screens

import com.example.capstoneapp.MapViewActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class MapActivity : AppCompatActivity() {

    private val userName = "YOUR_USERNAME_HERE" // 사용자 이름을 직접 설정해주세요

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, MapViewActivity::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish() // MapActivity는 더 이상 필요하지 않으므로 종료합니다
    }
}

private fun saveUserLocationToFirebase(userName: String, latitude: Double, longitude: Double) {
    val database = FirebaseDatabase.getInstance().reference.child("users")
    database.child(userName).setValue(mapOf("latitude" to latitude, "longitude" to longitude))
        .addOnSuccessListener {
            // Location saved successfully
        }
        .addOnFailureListener {
            // Failed to save location
        }
}