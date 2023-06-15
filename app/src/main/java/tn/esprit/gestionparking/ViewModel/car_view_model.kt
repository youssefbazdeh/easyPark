package tn.esprit.gestionparking.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.gestionparking.Model.car
import tn.esprit.gestionparking.Model.parking
import tn.esprit.gestionparking.Retrofit.ApiClient
import tn.esprit.gestionparking.Service.CarService
import tn.esprit.gestionparking.Service.ParkService

class car_view_model : ViewModel() {

    var addCarLiveData : MutableLiveData<car?> = MutableLiveData()
    var _addCarLiveData : LiveData<car?> = addCarLiveData

    fun addCar(car: car){
        val retrofit = ApiClient.getApiClient()!!.create(CarService::class.java)
        val addTask = retrofit.addCar(car)
        addTask.enqueue(object : Callback<car> {
            override fun onResponse(call: Call<car>, response: Response<car>) {
                if (response.isSuccessful){
                    addCarLiveData.postValue(response.body())
                }else{
                    Log.i("errorBody", response.errorBody()!!.string())
                    addCarLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<car>, t: Throwable) {
                addCarLiveData.postValue(null)
                Log.i("failure", t.message.toString())
            }
        })
    }


    /***************** AFFICHAGE BY ID USER *****************/
    var carLiveData: MutableLiveData<MutableList<car>?> = MutableLiveData()
    val _carLiveData : LiveData<MutableList<car>?> = carLiveData

    fun getAllCarByIdUser(user_id: String){
        val retrofit= ApiClient.getApiClient()!!.create(CarService::class.java)
        val getBudget=retrofit.getAllCarByIdUser(user_id)
        getBudget.enqueue(object : Callback<MutableList<car>> {
            override fun onResponse(call: Call<MutableList<car>>, response: Response<MutableList<car>>) {
                if (response.isSuccessful){
                    carLiveData.postValue(response.body())
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())

                    carLiveData.postValue(response.body())
                }

            }

            override fun onFailure(call: Call<MutableList<car>>, t: Throwable) {
                carLiveData.postValue(null)
                Log.i("failure",  t.message.toString())
            }

        })
    }



    var carOwnershipLiveData: MutableLiveData<MutableList<car>?> = MutableLiveData()
    val _carOwnershipLiveData : LiveData<MutableList<car>?> = carOwnershipLiveData


    fun getAllCarByOwnership(user_id: String,ownership: String){
        val retrofit= ApiClient.getApiClient()!!.create(CarService::class.java)
        val getChecklist=retrofit.getAllCarByOwnership(user_id,ownership)
        getChecklist.enqueue(object : Callback<MutableList<car>> {
            override fun onResponse(call: Call<MutableList<car>>, response: Response<MutableList<car>>) {
                if (response.isSuccessful){
                    carOwnershipLiveData.postValue(response.body())
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())

                    carOwnershipLiveData.postValue(response.body())
                }

            }

            override fun onFailure(call: Call<MutableList<car>>, t: Throwable) {
                carOwnershipLiveData.postValue(null)
                Log.i("failure",  t.message.toString())
            }

        })
    }

}