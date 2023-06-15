package tn.esprit.gestionparking.Service


import retrofit2.Call
import retrofit2.http.*
import tn.esprit.gestionparking.Model.ResponseUser
import tn.esprit.gestionparking.Model.User
import tn.esprit.gestionparking.Model.loginResponse



interface UserService {
    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("email") email:String,
              @Field("password") password:String
    ): Call<loginResponse>

    @GET("user/{id}")
    fun getUserById(
        @Path("id") id : String,
    ): Call<ResponseUser>


    @POST("user/signup")
    fun signUp(
        @Body user: User,
        ): Call<User>

    @PATCH("user/{user_id}")
    fun update(
        @Path("user_id") idUser : String,
       @Body user: User
    ): Call<User>
}