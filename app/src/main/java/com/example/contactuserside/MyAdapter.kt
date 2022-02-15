package com.example.contactuserside

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val userList : ArrayList<User>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item,
        parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = userList[position]

        holder.location.text = currentItem.location
        holder.inTime.text = currentItem.inTime
        holder.outTime.text = currentItem.outTime
        holder.readerUID.text = currentItem.readerUID

    }

    override fun getItemCount(): Int {

        return userList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val location : TextView = itemView.findViewById(R.id.loc)
        val inTime : TextView = itemView.findViewById(R.id.inti)
        val outTime : TextView = itemView.findViewById(R.id.outi)
        val readerUID : TextView = itemView.findViewById(R.id.uid)

    }

}