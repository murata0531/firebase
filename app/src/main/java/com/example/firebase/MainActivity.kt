package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val erroremail = findViewById<TextView>(R.id.erroremail)
        val errorpass = findViewById<TextView>(R.id.errorpass)

        val login = findViewById<Button>(R.id.register)
        val toregister = findViewById<Button>(R.id.tologin)

        toregister.setOnClickListener {

            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }
        login.setOnClickListener {

            val email = findViewById<EditText>(R.id.email)
            val emailtext = email.text.toString()

            val pass = findViewById<EditText>(R.id.pass)
            val passtext = pass.text.toString()

            val regex = Regex("^[A-Za-z]+\$")

            if(emailtext.length == 0 && passtext.length == 0){
                erroremail.text = "メールアドレスを入力してください"
                errorpass.text = "パスワードを入力してください"
            }else if(emailtext.length == 0){
                erroremail.text = "メールアドレスを入力してください"
            }else if(passtext.length == 0){
                errorpass.text = "パスワードを入力してください"
            }else if((regex.matches(passtext) == false && passtext.length <= 8) || (regex.matches(
                    passtext
                ) == false || passtext.length <= 8 ) ){
                errorpass.text = "パスワードは英字8文字以上で入力してください"
            }else {

                auth.signInWithEmailAndPassword(emailtext, passtext)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    baseContext, "Login 成功",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                errorpass.text = "メールアドレスまたはパスワードが違います"
                            }

                        }
            }

        }
    }

}