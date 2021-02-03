package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    val user = Firebase.auth.currentUser
    var auth_user_name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid


            val database = FirebaseDatabase.getInstance().getReference("user/" + uid.toString())

            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                   auth_user_name = snapshot.toString()
                }
                override fun onCancelled(error: DatabaseError) {
                    //ここにエラーがあった場合の処理を記述する
                    Toast.makeText(applicationContext,
                        "onCancelledが呼ばれました\n" + error.details, Toast.LENGTH_LONG).show()
                }
            })

            val username = findViewById<TextView>(R.id.textusername)
            val userid = findViewById<TextView>(R.id.textuserid)

            username.text = auth_user_name
            userid.text = uid.toString()
        }

    }
}