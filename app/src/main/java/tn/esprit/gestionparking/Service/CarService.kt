package tn.esprit.gestionparking.Service

import retrofit2.Call
import retrofit2.http.*
import tn.esprit.gestionparking.Model.car

interface CarService {

    @GET("vehicle/user/{user_id}")
    fun getAllCarByIdUser(
        @Path("user_id") user_id: String,
    ): Call<MutableList<car>>


    @GET("vehicle/{user_id}/{ownership}")
    fun getAllCarByOwnership(
        @Path("user_id") user_id: String,
        @Path("ownership") ownership: String,
    ): Call<MutableList<car>>

    @GET("vehicle/{id}")
    fun getCarById(
        @Path("id") id : String,
    ): Call<car>

    @POST("vehicle/user")
    fun addCar(
        @Body car: car
    ): Call<car>

    @PUT("vehicle/{idvehicle}")
    fun updateCar (
        @Path("idvehicle") idbudget : String,
        @Body car: car
    ): Call<car>

    @DELETE("vehicle/{idvehicle}")
    fun deleteCarById(
        @Path("idvehicle") idcar: String,
    ): Call<String>
}