package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.lang.System.exit

class AuthActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        auth = FirebaseAuth.getInstance()

        val erroremail = findViewById<TextView>(R.id.autherroremail)
        val errorpass = findViewById<TextView>(R.id.autherrorpass)
        val errorname = findViewById<TextView>(R.id.autherrorname)

        val register = findViewById<Button>(R.id.register)
        val tologin = findViewById<Button>(R.id.tologin)

        tologin.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        register.setOnClickListener {

            val email = findViewById<EditText>(R.id.authemail)
            val emailtext = email.text.toString()

            val pass = findViewById<EditText>(R.id.authpass)
            val passtext = pass.text.toString()

            val name = findViewById<EditText>(R.id.authname)
            val nametext = name.text.toString()

            val regex = Regex("^[A-Za-z]+\$")

            if(emailtext.length == 0 && passtext.length == 0 && nametext.length == 0){
                erroremail.text = "メールアドレスを入力してください"
                errorpass.text = "パスワードを入力してください"
                errorname.text = "名前を入力してください"
            }else if(nametext.length == 0){
                errorname.text = "名前を入力してください"
            } else if(emailtext.length == 0){
                erroremail.text = "メールアドレスを入力してください"
            }else if(passtext.length == 0){
                errorpass.text = "パスワードを入力してください"
            }else if((regex.matches(passtext) == false && passtext.length <= 8) || (regex.matches(passtext) == false || passtext.length <= 8 ) ){
                errorpass.text = "パスワードは英字8文字以上で入力してください"
            }else {

                auth.createUserWithEmailAndPassword(emailtext, passtext)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information

                                val user = auth.currentUser
                                Toast.makeText(baseContext, user.toString() + "さん、こんにいちは",
                                        Toast.LENGTH_SHORT).show()
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(baseContext, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show()
                            }

                            // ...
                        }
            }
        }
    }
}