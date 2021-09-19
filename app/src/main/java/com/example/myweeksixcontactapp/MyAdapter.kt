package com.example.myweeksixcontactapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MyAdapter(private var userList: ArrayList<UserData>):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private lateinit var  mListener :onItemClickListener



   inner class MyViewHolder(itemView:View, listener: onItemClickListener):RecyclerView.ViewHolder(itemView){
        var firstName : TextView = itemView.findViewById(R.id.firstNameRV)
        val lastName :TextView = itemView.findViewById(R.id.lastNameRV)
        var phone:TextView = itemView.findViewById(R.id.phone1)

       init {
           itemView.setOnClickListener{
               listener.onItemClick(adapterPosition)
           }
       }

    }

    interface onItemClickListener {

        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mListener  = listener

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    var itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)

        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    var cuItem = userList[position]



        holder.apply {



            firstName.text = cuItem.fullName
            phone.text = cuItem.contactNumber
        }
    }

    override fun getItemCount(): Int {
       return userList.size
    }



}