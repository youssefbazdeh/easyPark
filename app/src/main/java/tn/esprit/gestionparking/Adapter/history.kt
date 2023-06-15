package tn.esprit.gestionparking.Adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.gestionparking.Model.parking
import tn.esprit.gestionparking.R
import tn.esprit.gestionparking.Retrofit.ApiClient
import tn.esprit.gestionparking.Service.ParkService


class history (val context: Context, private val listParkings: MutableList<parking>) :
    RecyclerView.Adapter<history.ParkingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_parking, parent, false)
        return ParkingViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ParkingViewHolder, position: Int) {
        val parking = listParkings[position]
        holder.parkingPlace.setText(parking.place)
        holder.parkingPrice.setText(parking.price+ " TND")


        val experiencePopoupMenu= PopupMenu(context, holder.delete)
        experiencePopoupMenu.inflate(R.menu.popup_menu)


        experiencePopoupMenu.setOnMenuItemClickListener {
            when(it.itemId){

                R.id.delete->{
                    alertDialog(parking._id)
                    notifyItemRemoved(position)
                    true
                }
                else->true
            }
        }
        holder.delete.setOnClickListener {
            try {
                val popup= PopupMenu::class.java.getDeclaredField("experiencePopup")
                popup.isAccessible=true
                val menu=popup.get(experiencePopoupMenu)
                menu.javaClass.getDeclaredMethod("Show menu",Boolean::class.java).invoke(menu,true)
            }catch (e:Exception){
                e.printStackTrace()
            }
            finally {
                experiencePopoupMenu.show()
            }
        }
    }

    override fun getItemCount(): Int {
        return listParkings.size
    }
    class ParkingViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parkingPlace : TextView
        var parkingPrice : TextView
        var delete : ImageView

        init {
            parkingPlace = itemView.findViewById(R.id.place)
            parkingPrice = itemView.findViewById(R.id.price)
            delete = itemView.findViewById(R.id.delete)
        }
    }
    fun deleteBudgetById(idparking:String){
        val retrofit= ApiClient.getApiClient()!!.create(ParkService::class.java)
        val addUser=retrofit.deleteParkById(idparking)
        addUser.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    //   listExperiences.re
                    //    notifyItemRemoved()

                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())

                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {

                Log.i("failure",  t.message.toString())
            }

        })

    }
    fun alertDialog(idbudget: String) {

        //Setting message manually and performing action on button click
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("Voulez-vous vraiment supprimer cet reservation ?")
            .setCancelable(false)
            .setPositiveButton("Oui") { dialog, id ->
                deleteBudgetById(idbudget)
            }
            .setNegativeButton("Non") { dialog, id -> //  Action for 'NO' Button
                dialog.cancel()

            }
        //Creating dialog box
        val alert = builder.create()
        //Setting the title manually
        alert.setTitle("Deleted parking")
        alert.show()

    }

}