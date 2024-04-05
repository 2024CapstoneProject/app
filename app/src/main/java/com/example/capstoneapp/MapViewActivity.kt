package com.example.capstoneapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*

class MapViewActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        database = FirebaseDatabase.getInstance().reference.child("users")
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Default location (San Francisco)
        val defaultLocation = LatLng(37.7749, -122.4194)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))

        // Retrieve user's location from Firebase
        val userName = intent.getStringExtra("userName")
        userName?.let { name ->
            database.child(name).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val latitude = snapshot.child("latitude").value as? Double
                    val longitude = snapshot.child("longitude").value as? Double
                    if (latitude != null && longitude != null) {
                        val userLocation = LatLng(latitude, longitude)
                        googleMap.addMarker(MarkerOptions().position(userLocation).title(name))
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                    } else {
                        Toast.makeText(applicationContext, "User's location not found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, "Failed to retrieve user's location", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
