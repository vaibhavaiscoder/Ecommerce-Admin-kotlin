package com.example.ecomadmin.activity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecomadmin.R
import com.example.ecomadmin.databinding.ActivityAddressBinding
import com.example.ecomadmin.databinding.ActivityAllOrderBinding
import com.example.ecomadmin.model.UserDetailsModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressBinding

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        loadUserInfo()

        setContentView(binding.root)
    }


    val data = UserDetailsModel(userName =binding.userNameView.text.toString(),userPhoneNumber= binding.userPhoneNumberView.text.toString())

    private fun loadUserInfo() {
        Firebase.firestore.collection("users")
            .document(preferences.getString("orderId", "")!!)
            .get().addOnSuccessListener {
//                binding.userNameView.setText(it.getString("userName"))
//                binding.userPhoneNumberView.setText(it.getString("userPhoneNumber"))
                binding.villageView.setText(it.getString("village"))
                binding.cityView.setText(it.getString("city"))
                binding.pinCodeView.setText(it.getString("pinCode"))
                binding.stateView.setText(it.getString("state"))
            }
            .addOnFailureListener {

            }
    }
}