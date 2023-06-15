package tn.esprit.gestionparking.Model

data class car(
    var _id:String,
    var license: String? = null,
    var nickname: String? = null,
    var ownership: String,
    var user_id: String,
    var __v:Int=0
)