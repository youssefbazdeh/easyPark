package tn.esprit.gestionparking.Model

import java.util.*

data class User(
    var _id:String?=null,
    var username: String? = null,
    var email: String? = null,
    var password: String? = null,
    var __v:Int=0
)

data class ResponseUser (
    var _id:String?=null,
    var username: String? = null,
    var email: String? = null,
    var password: String? = null,
    var __v:Int
)

data class loginResponse (var message: String? = null,
                          var user:ResponseUser?=null,
                          var accessToken: String)