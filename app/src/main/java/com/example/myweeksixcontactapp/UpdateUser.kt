package com.example.myweeksixcontactapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.myweeksixcontactapp.databinding.ActivityMainBinding
import com.example.myweeksixcontactapp.databinding.ActivityUpdateUserBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UpdateUser : AppCompatActivity() {


    private lateinit var binding: ActivityUpdateUserBinding
    private lateinit var database: DatabaseReference
    private lateinit var ctButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

         binding = ActivityUpdateUserBinding.inflate(layoutInflater)

        var bundle: Bundle? = intent.extras
        var fName = intent.getStringExtra("firstName")
        var phone = intent.getStringExtra("phone")
        var id = intent.getStringExtra("id")

//        binding.firstName.text = fName
//        binding.phoneNumber.text = phone


         binding.updbutton.setOnClickListener {
             val firstName = binding.firstName.text.toString().trim()
             var phone = binding.phoneNumber.text.toString()
             val id = id.toString()

             updateData(firstName, phone, id)
         }


        }

    fun updateData(firstName: String, lastNmae: String, phone: String) {

    database = FirebaseDatabase.getInstance().getReference("contacts")
        var user = mapOf<String, String>(
            "firstName" to firstName,
            "phone" to phone)
        database.child(firstName).updateChildren(user).addOnSuccessListener {
            binding.firstName.text.clear()
            binding.lastName.text.clear()
            binding.phoneNumber.text.clear()

            Toast.makeText(this , "Update Successfull", Toast.LENGTH_SHORT)
        }.addOnFailureListener {
            Toast.makeText(this, "failed to update contact", Toast.LENGTH_LONG)
        }
    }

}