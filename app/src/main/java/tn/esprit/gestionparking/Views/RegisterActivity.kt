package tn.esprit.gestionparking.Views

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import tn.esprit.gestionparking.Model.User
import tn.esprit.gestionparking.R
import tn.esprit.gestionparking.ViewModel.login_view_model

class RegisterActivity : AppCompatActivity() {

    lateinit var login : TextView
    lateinit var button: Button
    lateinit var email: EditText
    lateinit var password : EditText
    lateinit var username : EditText
    lateinit var SignupViewModel : login_view_model
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        login = findViewById(R.id.login)
        prefs = getSharedPreferences(PREF_LOGIN,MODE_PRIVATE)
        button = findViewById(R.id.register_button)
        email = findViewById(R.id.email_et)
        password = findViewById(R.id.password_et)
        username = findViewById(R.id.username_et)

        button.setOnClickListener{
            if(validationEmail()&&validationPassword()&&validationUsername()){
                SignUp()
            }
        }

        login.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }



    private fun validationPassword(): Boolean {
        val password = password.text.toString().trim ()
        if(password.isEmpty()) {
            Toast.makeText(this, "obligatoire ", Toast.LENGTH_SHORT).show()
            return false
        } else {

            return true
        }}

    private fun validationEmail(): Boolean {
        val email = email.text.toString().trim()
        if(email.isEmpty()) {
            Toast.makeText(this, "obligatoire ", Toast.LENGTH_SHORT).show()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "verifier votre adresse", Toast.LENGTH_SHORT).show()
            return false
        } else {

            return true
        }}



    private fun validationUsername(): Boolean {
        val username = username.text.toString().trim ()
        if(username.isEmpty()) {
            Toast.makeText(this, "obligatoire ", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }}

    private  fun SignUp() {
        val user = User("",username.text.toString().trim(),email.text.toString().trim(),password.text.toString().trim(),0)
        SignupViewModel= ViewModelProvider(this).get(login_view_model::class.java)
        SignupViewModel.signUp(user)
        SignupViewModel._signUpLiveData.observe(this, androidx.lifecycle.Observer<User?>{
            if (it!=null){
                val intent = Intent(this,login::class.java)
                Toast.makeText(applicationContext, "ajout succes !", Toast.LENGTH_LONG).show()
                startActivity(intent)


            }else{
                Toast.makeText(applicationContext, "signup failed !", Toast.LENGTH_LONG).show()
            }
        })
    }
}