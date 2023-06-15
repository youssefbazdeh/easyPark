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
import tn.esprit.gestionparking.R
import tn.esprit.gestionparking.Retrofit.ApiClient
import tn.esprit.gestionparking.Service.CarService
import tn.esprit.gestionparking.Service.ParkService

class car(val context: Context, private val listCars: MutableList<tn.esprit.gestionparking.Model.car>) :
    RecyclerView.Adapter<car.CarViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false)
        return CarViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = listCars[position]
        holder.parkingPlace.setText(car.license)
        holder.parkingPrice.setText(car.nickname)
        holder.owned.setText(car.ownership)


        val experiencePopoupMenu= PopupMenu(context, holder.delete)
        experiencePopoupMenu.inflate(R.menu.popup_menu)


        experiencePopoupMenu.setOnMenuItemClickListener {
            when(it.itemId){

                R.id.delete->{
                    alertDialog(car._id)
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
        return listCars.size
    }
    class CarViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parkingPlace : TextView
        var parkingPrice : TextView
        var delete : ImageView
        var owned : TextView

        init {
            parkingPlace = itemView.findViewById(R.id.place)
            parkingPrice = itemView.findViewById(R.id.price)
            delete = itemView.findViewById(R.id.delete)
            owned = itemView.findViewById(R.id.owned)
        }
    }
    fun deleteCarById(idcar:String){
        val retrofit= ApiClient.getApiClient()!!.create(CarService::class.java)
        val addUser=retrofit.deleteCarById(idcar)
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
    fun alertDialog(idcar: String) {

        //Setting message manually and performing action on button click
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("Voulez-vous vraiment supprimer cet reservation ?")
            .setCancelable(false)
            .setPositiveButton("Oui") { dialog, id ->
                deleteCarById(idcar)
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