package tn.esprit.gestionparking.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButtonToggleGroup
import tn.esprit.gestionparking.Adapter.history
import tn.esprit.gestionparking.Model.car
import tn.esprit.gestionparking.Model.parking
import tn.esprit.gestionparking.R
import tn.esprit.gestionparking.ViewModel.car_view_model
import tn.esprit.gestionparking.ViewModel.parking_view_model

class ParkingActivity : AppCompatActivity() {


    lateinit var rvParking : RecyclerView
    lateinit var viewmodel : car_view_model
    lateinit var listCar: MutableList<car>
    lateinit var comp : Button
    lateinit var prog : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking)
        viewmodel = ViewModelProvider(this).get(car_view_model::class.java)
        comp = findViewById(R.id.button_completed)
        prog = findViewById(R.id.button_prog)
        rvParking = findViewById(R.id.rv)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvParking.layoutManager = layoutManager
        listCar = ArrayList()

        val toggleButtonGroup=findViewById<MaterialButtonToggleGroup>(R.id.toggleButtonGroup)
        toggleButtonGroup.check(R.id.button_completed)
        getAllCarByOwnership(this.getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!,"Owned")

        toggleButtonGroup.addOnButtonCheckedListener { toggleButtonGroup, checkedId, isChecked ->
            if(isChecked){
                when(checkedId){
                    R.id.button_completed->  getAllCarByOwnership(this.getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!,"Owned")
                    R.id.button_prog->  getAllCarByOwnership(this.getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!,"Not Owned")
                }
            }else{

                Toast.makeText(this,"Nothing", Toast.LENGTH_SHORT).show()

            }
        }

        getAllCarsByIdUser(this.getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!)

    }




    fun getAllCarsByIdUser(user_id: String) {

        viewmodel.getAllCarByIdUser(user_id)
        viewmodel._carLiveData.observe(this, Observer<MutableList<car>?>{
            if (it != null) {
                if (it.size>0){

                    listCar=it
                    val layoutManagerBudget = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
                    rvParking.layoutManager = layoutManagerBudget
                    val budgetAdapter = tn.esprit.gestionparking.Adapter.car(this,listCar)
                    rvParking.adapter =budgetAdapter
                    budgetAdapter.notifyItemChanged(listCar.size+1)
                }
            }
        })


    }


    fun getAllCarByOwnership(user_id: String,ownership : String) {

        viewmodel.getAllCarByOwnership(user_id,ownership)
        viewmodel._carOwnershipLiveData.observe(this, Observer<MutableList<car>?>{
            if (it != null) {
                if (it.size>0){

                    listCar=it
                    val layoutManagerCompetences= LinearLayoutManager(this,RecyclerView.VERTICAL,false)
                    rvParking.layoutManager=layoutManagerCompetences
                    val checklistAdapter = tn.esprit.gestionparking.Adapter.car(this,listCar)
                    rvParking.adapter =checklistAdapter
                    checklistAdapter.notifyItemChanged(listCar.size+1)
                }
            }
        })


    }
}