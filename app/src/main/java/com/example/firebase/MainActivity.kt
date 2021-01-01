package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val login = findViewById<Button>(R.id.login)

        login.setOnClickListener {

            val email = findViewById<EditText>(R.id.email)
            val emailtext = email.text.toString()

            val pass = findViewById<EditText>(R.id.pass)
            val passtext = pass.text.toString()

            auth.signInWithEmailAndPassword(emailtext, passtext)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                    baseContext, "Login 成功",
                                    Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val errortext = findViewById<TextView>(R.id.errortext)
                            errortext.text = "メールアドレスまたはパスワードが違います"
                        }

                    }
        }
    }
}