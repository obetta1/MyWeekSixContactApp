package com.example.myweeksixcontactapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.myweeksixcontactapp.databinding.ActivityMainBinding
import com.example.myweeksixcontactapp.secondimplimentation.ReadContackActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase




class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var  myRef :DatabaseReference
   private lateinit var database: FirebaseDatabase
    private lateinit var ctButton :Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.upbutton.setOnClickListener {
            val intent = Intent(this, UpdateUser::class.java)
            startActivity(intent)
        }



        binding.button2.setOnClickListener {
            val intent = Intent(this, ReadContackActivity::class.java)
            startActivity(intent)
        }

         database = Firebase.database
        binding.ctButton.setOnClickListener{
            val intent = Intent(this, UserlistActivity::class.java )
            startActivity(intent)
        }




        myRef = Firebase.database.getReference("contacts")
        // binding the views with view binding
        binding.button.setOnClickListener {
            val firstName = binding.firstName.text.toString()
            val phone = binding.phoneNumber.text.toString()
            val id = myRef.push().key



            // create the object of the user class
            val user = UserData(phone,firstName,id!!)
            myRef.child(id!!).setValue(user).addOnSuccessListener {

                binding.firstName.text.clear()
                binding.lastName.text.clear()
                binding.phoneNumber.text.clear()


                Toast.makeText(this, "Successfully Saved ", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(this, " Failed to Save", Toast.LENGTH_SHORT).show()
            }
        }
    }
}