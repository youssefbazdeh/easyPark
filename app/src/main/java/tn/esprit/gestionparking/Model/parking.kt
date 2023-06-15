package tn.esprit.gestionparking.Model

data class parking(
    var _id:String,
    var place: String? = null,
    var price: String? = null,
    var user_id: String,
    var __v:Int=0
)