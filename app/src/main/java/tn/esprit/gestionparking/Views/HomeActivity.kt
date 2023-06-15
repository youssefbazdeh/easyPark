package tn.esprit.gestionparking.Views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import tn.esprit.gestionparking.Adapter.history
import tn.esprit.gestionparking.Adapter.static
import tn.esprit.gestionparking.Model.parking
import tn.esprit.gestionparking.R
import tn.esprit.gestionparking.ViewModel.parking_view_model

class HomeActivity : AppCompatActivity() {



    var arrayAdapter: ArrayAdapter<String>? = null
    lateinit var listNote: java.util.ArrayList<String>
    lateinit var add : Button
    lateinit var username : TextView
    lateinit var park : AutoCompleteTextView
    lateinit var viewmodel : parking_view_model
    lateinit var rvParking : RecyclerView
    lateinit var logout : ImageView
    lateinit var listParking: MutableList<parking>


    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        logout = findViewById(R.id.logout)
        add = findViewById(R.id.add)
        rvParking = findViewById(R.id.recyclerView1)
        park = findViewById(R.id.park)

        username = findViewById(R.id.username)
        viewmodel = ViewModelProvider(this).get(parking_view_model::class.java)


        logout.setOnClickListener {
            val preferences: SharedPreferences = this.getSharedPreferences(
                PREF_LOGIN, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor=preferences.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(this,LoginActivity::class.java))
        }

        username.setText(this.getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(
            USERNAME,"").toString().trim())
        /*******************************/
        val rootView = window.decorView.rootView

        // Set the system UI visibility flags to hide the navigation bar and enable fullscreen mode
        rootView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
        /*******************************/

        add.setOnClickListener {
            addPark(this.getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(
                ID,"").toString().trim())
            getAllParkingByIdUser(this.getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!)

        }


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item1 -> {
                    // Handle item 1 selection
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_item2 -> {
                    // Handle item 2 selection
                    val intent = Intent(this, CarActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_item3 -> {
                    // Handle item 3 selection
                    val intent = Intent(this, ParkingActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
        /*******************************/
        serviceTypeDropdown()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        val itemList: MutableList<String> = ArrayList()
        itemList.add("Nearest")
        itemList.add("Malls")
        itemList.add("Park&Go")
        itemList.add("Hubs")

        val adapter = static(itemList)
        recyclerView.adapter = adapter


        getAllParkingByIdUser(this.getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!)
    }

    private fun serviceTypeDropdown(){
        listNote = java.util.ArrayList<String>()
        listNote.add("Lac1")
        listNote.add("Aouina")
        listNote.add("centre ville")
        listNote.add("ghazella")
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


    fun getAllParkingByIdUser(user_id: String) {

        viewmodel.getAllParkingByIdUser(user_id)
        viewmodel._parkingLiveDataa.observe(this, Observer<MutableList<parking>?>{
            if (it != null) {
                if (it.size>0){

                    listParking=it
                    val layoutManagerBudget = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
                    rvParking.layoutManager = layoutManagerBudget
                    val budgetAdapter = history(this,listParking)
                    rvParking.adapter =budgetAdapter
                    budgetAdapter.notifyItemChanged(listParking.size+1)
                }
            }
        })


    }

    private fun addPark(user_id : String){
        val no = park.text.toString().trim()
        val park = parking("",no,"5",user_id,0)
        viewmodel.addParking(park)
        viewmodel._addParkLiveData.observe(this, Observer<parking?>{
            Log.i("aaaaaaaaaaa",it.toString())
            if (it!=null){
                Toast.makeText(applicationContext,"Ajouter park success !!",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext,"Ajouter park failed !!",Toast.LENGTH_SHORT).show()

            }
        })


    }
}