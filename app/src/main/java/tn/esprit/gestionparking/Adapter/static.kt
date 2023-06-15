package tn.esprit.gestionparking.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.gestionparking.R



class static(private val itemList: List<String>) :
    RecyclerView.Adapter<static.StaticViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaticViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_static, parent, false)
        return StaticViewHolder(view)
    }

    override fun onBindViewHolder(holder: StaticViewHolder, position: Int) {
        val item = itemList[position]
        holder.textView.text = item

        when (position) {
            0 -> holder.image.setImageResource(R.drawable.placeholder)
            1 -> holder.image.setImageResource(R.drawable.shopping_cart)
            2 -> holder.image.setImageResource(R.drawable.car_park)
            3 -> holder.image.setImageResource(R.drawable.network)
            // Add more cases as needed
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class StaticViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView
        var image: ImageView

        init {
            textView = itemView.findViewById(R.id.text)
            image = itemView.findViewById(R.id.image)
        }
    }
}
