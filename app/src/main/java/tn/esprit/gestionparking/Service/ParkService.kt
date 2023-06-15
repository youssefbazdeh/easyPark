package tn.esprit.gestionparking.Service

import retrofit2.Call
import retrofit2.http.*
import tn.esprit.gestionparking.Model.parking

interface ParkService {
    @GET("parking/user/{user_id}")
    fun getAllParkByIdUser(
        @Path("user_id") user_id: String,
    ): Call<MutableList<parking>>

    @GET("parking/{id}")
    fun getParkById(
        @Path("id") id : String,
    ): Call<parking>

    @POST("parking/user")
    fun addPark(
        @Body parking: parking
    ): Call<parking>



    @DELETE("parking/{idparking}")
    fun deleteParkById(
        @Path("idparking") idparking: String,
    ): Call<String>


}