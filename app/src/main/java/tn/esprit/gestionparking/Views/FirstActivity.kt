package tn.esprit.gestionparking.Views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3
import tn.esprit.gestionparking.Adapter.first
import tn.esprit.gestionparking.R

class FirstActivity : AppCompatActivity() {

    lateinit var start : Button
    lateinit var viewPager2: ViewPager2
    private var imagesList = mutableListOf<Int>()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        start = findViewById(R.id.start)
        start.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        postToList()

        viewPager2 = findViewById<ViewPager2>(R.id.view_pager2)
        viewPager2.adapter = first(imagesList)
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val indicator = findViewById<CircleIndicator3>(R.id.ind)
        indicator.setViewPager(viewPager2)
    }


    private fun addToList(image: Int) {
        imagesList.add(image)
    }
    private fun postToList(){

        addToList(R.drawable.car1)
        addToList(R.drawable.car2)
        addToList(R.drawable.car3)

    }
}