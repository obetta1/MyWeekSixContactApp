package com.example.myweeksixcontactapp.secondimplimentation

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract.Instances.query

import android.provider.ContactsContract
import android.provider.MediaStore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.example.myweeksixcontactapp.R

class ReadContackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_read_contack)

        var contButton:Button = findViewById(R.id.contactButton)
    var recyclerView = findViewById<RecyclerView>(R.id.contactList)

     recyclerView.layoutManager  = LinearLayoutManager(this)

        contButton.setOnClickListener {
            requestPermission()



            // varify permission
            val contactList: MutableList<ContactDT> = ArrayList()
            val contacts = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )
            while (contacts?.moveToNext() == true) {
                val name =
                    contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val number =
                    contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val obj = ContactDT(name, number)
                obj.name = name
                obj.number = number

                var photo_uri =
                    contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))

                if (photo_uri != null) {
//                    obj.image =MediaStore.Images.Media.getContentUri()
                }
                contactList.add(obj)
            }
            recyclerView.adapter = ContactAdapter(contactList, this)
            contacts?.close()

        }


    }





    private fun requestPermission() {
        if (
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // check if permissions are granted on install time
                checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        ) {

            // read contacts if permission is granted
            //readContact()
            Toast.makeText(this, "Read Contact permission granted", Toast.LENGTH_SHORT).show()
        } else {
            if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // ask for permission at run time
                    shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)
                } else {
                    Toast.makeText(
                        this,
                        "Call permission is required to make phone calls with this app",
                        Toast.LENGTH_SHORT
                    ).show()
                    TODO("VERSION.SDK_INT < M")
                }
            ) {
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), 111)
            }
        }
    }





    class ContactAdapter (items: List<ContactDT>, ctx:Context):RecyclerView.Adapter<ContactAdapter.ViewHolder>(){

        private var list:List<ContactDT> = items
        private var context:Context = ctx

        class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val name:TextView = itemView.findViewById(R.id.tvName)
            val number :TextView = itemView.findViewById(R.id.tvNumber)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ViewHolder {

            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.contact_child, parent,false))
        }

        override fun onBindViewHolder(holder: ContactAdapter.ViewHolder, position: Int) {
            holder.name.text =list[position].name
            holder.number.text =list[position].number


        }

        override fun getItemCount(): Int {
        return list.size
        }
    }

}