package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
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

        val textView = TextView(this)

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


            val username = findViewById<TextView>(R.id.textusername)

            val database = FirebaseDatabase.getInstance().getReference("user/" + uid.toString())

            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                   auth_user_name = snapshot.value.toString()
                    username.text = "ログイン：" + auth_user_name
                }
                override fun onCancelled(error: DatabaseError) {
                    //ここにエラーがあった場合の処理を記述する
                    Toast.makeText(applicationContext,
                        "onCancelledが呼ばれました\n" + error.details, Toast.LENGTH_LONG).show()
                }
            })

        }

        val database = FirebaseDatabase.getInstance().getReference("messages")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                textView.text = snapshot.value.toString()
                val scroll = findViewById<LinearLayout>(R.id.scroll)

                scroll.addView(textView)
            }
            override fun onCancelled(error: DatabaseError) {
                //ここにエラーがあった場合の処理を記述する
                Toast.makeText(applicationContext,
                    "onCancelledが呼ばれました\n" + error.details, Toast.LENGTH_LONG).show()
            }
        })

        val ref = database.getRef()

        val send = findViewById<ImageButton>(R.id.send)
        val edit = findViewById<EditText>(R.id.edit)

        send.setOnClickListener {
            ref.setValue(edit.text.toString())
        }
    }

}