package com.example.myweeksixcontactapp

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DeleteUser : AppCompatActivity() {
     lateinit var firstName:TextView
     lateinit var phoneNumber:TextView

    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_user)

         firstName = findViewById<TextView>(R.id.firstname)
         phoneNumber = findViewById<TextView>(R.id.phoneNumber)
        val phoneicon = findViewById<ImageView>(R.id.phoneIcon)
        val d_Button = findViewById<Button>(R.id.d_button)
        val updateicon = findViewById<ImageView>(R.id.updateIcon)
        var deleteicon = findViewById<ImageView>(R.id.deleteIcon)
        val addicon = findViewById<ImageView>(R.id.addIcon)



        var bundle: Bundle? = intent.extras
        var fName = intent.getStringExtra("firstName")
        var id = intent.getStringExtra("id")
        var phone = intent.getStringExtra("phone")

        firstName.text = fName
        phoneNumber.text = phone


// request a call phone permision and make a call

        updateicon.setOnClickListener {
            intent = Intent(this@DeleteUser, UpdateUser::class.java )
            intent.putExtra("firstName", fName.toString())
            intent.putExtra("phone", phone.toString())
            intent.putExtra("id", id.toString())

            startActivity(intent)
        }


        addicon.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        phoneicon.setOnClickListener {

            Toast.makeText(this, "item click", Toast.LENGTH_SHORT).show()
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    Array(1) { android.Manifest.permission.CALL_PHONE },
                    111
                )
            } else makeCall(phone)

        }

        d_Button.setOnClickListener{
                database = Firebase.database.getReference("contacts")
                database.child(id!!).removeValue().addOnSuccessListener {
                    firstName.clearComposingText()
                    phoneNumber.clearComposingText()
                }.addOnFailureListener {
                }

        }
    }

//    fun deleteData(userName:String){
//        database = Firebase.database.getReference("contacts")
//        database.child(userName).removeValue().addOnSuccessListener {
//            Toast.makeText(this,"seccessfully deleted", Toast.LENGTH_LONG).show()
//            firstName.clearComposingText()
//            phoneNumber.clearComposingText()
//        }.addOnFailureListener {
//            Toast.makeText(this, "failed to delete user", Toast.LENGTH_SHORT)
//        }
//
//    }

// this fnction is responsible for making a call
private fun makeCall(number: String?) {
    val intent = Intent(Intent.ACTION_CALL)
    intent.data = Uri.parse("tel:$number")
    startActivity(intent)
}
}