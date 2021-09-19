package com.example.myweeksixcontactapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserlistActivity : AppCompatActivity() {
    lateinit var userList: ArrayList<UserData>
    lateinit var dbRef: DatabaseReference
    lateinit var userRecyclerView: RecyclerView
    lateinit var database: FirebaseDatabase
    lateinit var adapter: MyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userlist)


        dbRef = Firebase.database.reference
        userRecyclerView = findViewById(R.id.userlist)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        userList = arrayListOf()
        adapter = MyAdapter(userList)
        userRecyclerView.adapter = adapter

        getUserData()


        adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

                var intent = Intent(this@UserlistActivity, DeleteUser::class.java).apply {
                    putExtra("firstName", userList[position].fullName.toString())
                    putExtra("phone", userList[position].contactNumber.toString())
                    putExtra("id",userList[position].id.toString() )
                }


                startActivity(intent)

            }
        })


    }

    fun getUserData() {

        Toast.makeText(this@UserlistActivity, " calls userdata", Toast.LENGTH_LONG).show()
        dbRef.child("contacts").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                Toast.makeText(this@UserlistActivity, " data changes", Toast.LENGTH_LONG).show()
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(UserData::class.java)
                        userList.add(user!!)
                        Toast.makeText(this@UserlistActivity, "bsvhsvc", Toast.LENGTH_SHORT).show()
                    }


                    adapter.notifyDataSetChanged()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@UserlistActivity, " Error", Toast.LENGTH_LONG).show()
            }

        })


    }


}






