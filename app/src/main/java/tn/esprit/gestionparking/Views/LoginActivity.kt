package tn.esprit.gestionparking.Views

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import tn.esprit.gestionparking.Model.loginResponse
import tn.esprit.gestionparking.R
import tn.esprit.gestionparking.ViewModel.login_view_model

const val ID="id"
const val PREF_LOGIN ="Remember Me"
const val  EMAIL="Email"
const val PASSWORD="Password"
const val TOKEN="Token"
const val  USERNAME="Username"
const val  USER="User"

class LoginActivity : AppCompatActivity() {

    lateinit var button: Button
    lateinit var email: EditText
    lateinit var password : EditText
    lateinit var loginViewModel : login_view_model
    lateinit var prefs: SharedPreferences
    lateinit var register : TextView



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prefs = getSharedPreferences(PREF_LOGIN,MODE_PRIVATE)
        button = findViewById(R.id.login_button)
        email = findViewById(R.id.email_et)
        password = findViewById(R.id.password_et)
        register = findViewById(R.id.register)

        register.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        if (prefs.getString(USERNAME,"")!!.isNotEmpty() and prefs.getString(PASSWORD,"")!!.isNotEmpty()){
            val intent = Intent(applicationContext,HomeActivity::class.java)
            startActivity(intent)
        }


        button.setOnClickListener{

            login()

        }

    }



    fun login(){
        loginViewModel = ViewModelProvider(this).get(login_view_model::class.java)
        loginViewModel.login(email.text.toString().trim(),password.text.toString().trim())
        loginViewModel._logingLiveData.observe(this, Observer<loginResponse?>{
            if (it!=null){
                //Edit the SharedPreferences by putting all the data
                prefs.edit().apply(){
                    putString(ID, it.user?._id)
                    putString(USERNAME,it.user?.username)
                    putString(PASSWORD,it.user?.password)
                    putString(TOKEN,it.accessToken)
                    putString(EMAIL,it.user?.email)
                    apply()
                }


                Toast.makeText(applicationContext, "Login succes !"+it.accessToken, Toast.LENGTH_LONG).show()
                startActivity(Intent(this,HomeActivity::class.java))
                finish()

            }else{
                Toast.makeText(applicationContext, "Login failed !", Toast.LENGTH_LONG).show()
            }
        })
    }
}