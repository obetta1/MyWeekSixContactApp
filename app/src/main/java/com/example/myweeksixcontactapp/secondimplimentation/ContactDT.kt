package com.example.myweeksixcontactapp.secondimplimentation

import android.graphics.Bitmap

data class ContactDT(

    var name : String,
    var number: String,
    var image: Bitmap? = null
)
