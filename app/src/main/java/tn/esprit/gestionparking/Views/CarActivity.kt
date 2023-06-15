package tn.esprit.gestionparking.Views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import tn.esprit.gestionparking.Model.car
import tn.esprit.gestionparking.R
import tn.esprit.gestionparking.ViewModel.car_view_model

class CarActivity : AppCompatActivity() {

    lateinit var add : Button
    lateinit var lic : EditText
    lateinit var nic : EditText
    lateinit var addCarViewModel: car_view_model
    var arrayAdapter: ArrayAdapter<String>? = null
    lateinit var listNote: java.util.ArrayList<String>
    lateinit var park : AutoCompleteTextView



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)

        park = findViewById(R.id.park)
        lic = findViewById(R.id.lic)
        nic = findViewById(R.id.nic)
        add = findViewById(R.id.add)
        serviceTypeDropdown()
        add.setOnClickListener {
            Log.d("teeeeeest" , this.getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(
            ID,"").toString().trim())
            addCarByIdUser(this.getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(
                ID,"").toString().trim())
            Toast.makeText(applicationContext, "button clicked !", Toast.LENGTH_LONG).show()
        }
    }


    private fun addCarByIdUser(user_id: String) {
        addCarViewModel = ViewModelProvider(this).get(car_view_model::class.java)
        val no = park.text.toString().trim()
        val lic = lic.text.toString().trim()
        val nic = nic.text.toString().trim()
        val car = car("",lic,nic,no,user_id,0)


        addCarViewModel.addCar(car)
        addCarViewModel._addCarLiveData.observe(this, Observer<car?>{
            if (it!=null){
                Toast.makeText(applicationContext, "ajout succes !", Toast.LENGTH_LONG).show()
                finish()

            }else{
                Toast.makeText(applicationContext, "ajout failed !", Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun serviceTypeDropdown(){
        listNote = java.util.ArrayList<String>()
        listNote.add("Owned")
        listNote.add("Not Owned")
        arrayAdapter = ArrayAdapter<String>(
            this,
            R.layout.item_dropdown,
            listNote
        )
        park.setAdapter(arrayAdapter)
        park.setOnItemClickListener(object :
            AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(
                p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            }
        })
    }
}