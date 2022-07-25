package com.azizbek.salon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.animation.content.Content
import com.google.firebase.database.core.Context

class UserAdapter constructor (
    val context:android.content.Context,
    val arrayList: ArrayList<Navbat>
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.layaut,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.apply {
            textView.setText("${arrayList.get(position).clientname} ${arrayList.get(position).clientsurname}")
            textView2.setText("${arrayList.get(position).clientstartime} до ${arrayList.get(position).clientstoptime}")
            animation.playAnimation()

        }

    }

    override fun getItemCount(): Int {

        return arrayList.size
    }
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView=itemView.findViewById<TextView>(R.id.textviewname)
        val textView2=itemView.findViewById<TextView>(R.id.textviewtime)
        val animation=itemView.findViewById<LottieAnimationView>(R.id.lottlelayaut)
    }
}