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

class parking_view_model : ViewModel(){
    var addParkLiveData : MutableLiveData<parking?> = MutableLiveData()
    var _addParkLiveData : LiveData<parking?> = addParkLiveData

    fun addParking(parking: parking){
        val retrofit = ApiClient.getApiClient()!!.create(ParkService::class.java)
        val addPark = retrofit.addPark(parking)
        addPark.enqueue(object : Callback<parking> {
            override fun onResponse(call: Call<parking>, response: Response<parking>) {
                if (response.isSuccessful){
                    addParkLiveData.postValue(response.body())
                }else{
                    Log.i("errorBody", response.errorBody()!!.string())
                    addParkLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<parking>, t: Throwable) {
                addParkLiveData.postValue(null)
                Log.i("failure", t.message.toString())
            }
        })
    }


    /***************** AFFICHAGE BY ID USER *****************/
    var parkingLiveData: MutableLiveData<MutableList<parking>?> = MutableLiveData()
    val _parkingLiveDataa : LiveData<MutableList<parking>?> = parkingLiveData

    fun getAllParkingByIdUser(user_id: String){
        val retrofit= ApiClient.getApiClient()!!.create(ParkService::class.java)
        val getBudget=retrofit.getAllParkByIdUser(user_id)
        getBudget.enqueue(object : Callback<MutableList<parking>> {
            override fun onResponse(call: Call<MutableList<parking>>, response: Response<MutableList<parking>>) {
                if (response.isSuccessful){
                    parkingLiveData.postValue(response.body())
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())

                    parkingLiveData.postValue(response.body())
                }

            }

            override fun onFailure(call: Call<MutableList<parking>>, t: Throwable) {
                parkingLiveData.postValue(null)
                Log.i("failure",  t.message.toString())
            }

        })
    }
}