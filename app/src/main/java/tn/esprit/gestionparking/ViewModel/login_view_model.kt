package tn.esprit.gestionparking.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.gestionparking.Model.User
import tn.esprit.gestionparking.Model.loginResponse
import tn.esprit.gestionparking.Retrofit.ApiClient
import tn.esprit.gestionparking.Service.UserService


class login_view_model : ViewModel() {

    var loginLiveData : MutableLiveData<loginResponse?> = MutableLiveData()
    var _logingLiveData : LiveData<loginResponse?> = loginLiveData

    fun login(email:String,password:String){

        val retrofit= ApiClient.getApiClient()!!.create(UserService::class.java)
        val addUser=retrofit.login(email,password)
        addUser.enqueue(object : Callback<loginResponse> {



            override fun onResponse(
                call: Call<loginResponse>,
                response: Response<loginResponse>
            ) {
                if (response.isSuccessful){
                    loginLiveData.postValue(response.body())
                }else{
                    Log.i("test",  response.errorBody()!!.string())

                    loginLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<loginResponse>, t: Throwable) {
                loginLiveData.postValue(null)
                Log.i("failure", t.message.toString())                        }
        })
    }


    var signUpLiveData: MutableLiveData<User?> = MutableLiveData()
    val _signUpLiveData : LiveData<User?> = signUpLiveData

    fun signUp(user:User){
        val retrofit= ApiClient.getApiClient()!!.create(UserService::class.java)
        val addUser=retrofit.signUp(user)
        addUser.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful){
                    signUpLiveData.postValue(response.body())
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())

                    signUpLiveData.postValue(response.body())
                }

            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                signUpLiveData.postValue(null)
                Log.i("failure",  t.message.toString())
            }

        })
    }

}