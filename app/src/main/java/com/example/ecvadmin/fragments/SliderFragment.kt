package com.example.ecomadmin.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.ecomadmin.R
import com.example.ecomadmin.adapter.AddProductImageAdapter
import com.example.ecomadmin.databinding.FragmentSliderBinding
import com.example.ecomadmin.model.AddProductModel
import com.example.ecomadmin.model.SliderModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class SliderFragment : Fragment() {

//    private lateinit var binding : FragmentSliderBinding
//    private var imageUrl : Uri? = null
//    private lateinit var dialog: Dialog

//    private var launchGalleryActivity = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ){
//        if (it.resultCode == Activity.RESULT_OK){
//            imageUrl = it.data!!.data
//            binding.imageView.setImageURI(imageUrl)
//        }
//    }

//    new code

    private lateinit var binding: FragmentSliderBinding

    private lateinit var list: ArrayList<Uri>
    private lateinit var listImages: ArrayList<String>
    private lateinit var adapter: AddProductImageAdapter
    private lateinit var dialog: Dialog

    private var launchProductActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val imageUrl = it.data!!.data
            list.add(imageUrl!!)
            adapter.notifyDataSetChanged()
        }
    }

    //...

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSliderBinding.inflate(layoutInflater)

        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.progress_layout)
        dialog.setCancelable(false)

        //..
        list = ArrayList()
        listImages = ArrayList()
        //..

        binding.productImgBtn.setOnClickListener {
            val intent = Intent("android.intent.action.GET_CONTENT")
            intent.type = "image/*"
            launchProductActivity.launch(intent)
        }

        adapter = AddProductImageAdapter(list)
        binding.productImgRecyclerView.adapter = adapter

        binding.submitProductBtn.setOnClickListener {
            validateData()
        }


//        binding.apply {
//            imageView.setOnClickListener {
//                val intent = Intent("android.intent.action.GET_CONTENT")
//                intent.type = "image/*"
//                launchGalleryActivity.launch(intent)
//            }
//            btUpload.setOnClickListener {
//                if(imageUrl != null){
//                    uploadImage(imageUrl!!)
//                }else{
//                    Toast.makeText(requireContext(), "Please select image", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
        return binding.root
    }


    private fun validateData() {
        if (list.size < 1) {
            Toast.makeText(requireContext(), "Please select slider images", Toast.LENGTH_SHORT).show()
        } else {
            uploadProductImage()
        }
    }


    private var i = 0

    private fun uploadProductImage() {
        dialog.show()

        val fileName = UUID.randomUUID().toString() + ".jpg"
        val refStorage = FirebaseStorage.getInstance().reference.child("slider/$fileName")
        refStorage.putFile(list[i])
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { image ->
                    listImages.add(image!!.toString())
                    if (list.size == listImages.size) {
                        storeData()
                    } else {
                        i +=1
                        uploadProductImage()
                    }
                }
            }
            .addOnFailureListener {
                dialog.dismiss()
                Toast.makeText(
                    requireContext(),
                    "Something went wrong with storage.",
                    Toast.LENGTH_SHORT
                ).show()

            }
    }


//    private fun storeData(image: String) {
//        val db = Firebase.firestore
//
//        val data = hashMapOf<String, Any>(
//            "img" to image
//        )
//
//        db.collection("slider").document("item").set(data)
//            .addOnSuccessListener {
//                dialog.dismiss()
//                Toast.makeText(requireContext(),"Slider Updated", Toast.LENGTH_SHORT).show()
//            }.addOnFailureListener {
//                dialog.dismiss()
//                Toast.makeText(requireContext(),"Something went Wrong", Toast.LENGTH_SHORT).show()
//            }
//
//    }

    private fun storeData() {
        val db = Firebase.firestore.collection("slider")
        val key =db.document().id

        val data = SliderModel(
            key,
            listImages
        )
//        db.document(key).set(data).addOnSuccessListener {
        db.document("item").set(data).addOnSuccessListener {

            dialog.dismiss()
            Toast.makeText(requireContext(),"Slider Added",Toast.LENGTH_SHORT).show()
        }
            .addOnSuccessListener {
                dialog.dismiss()
                Toast.makeText(requireContext(),"Slider Added",Toast.LENGTH_SHORT).show()
            }
    }

//    private fun uploadImage(uri: Uri) {
//        dialog.show()
//
//        val fileName = UUID.randomUUID().toString()+".jpg"
//        val refStorage = FirebaseStorage.getInstance().reference.child("slider/$fileName")
//        refStorage.putFile(uri)
//            .addOnSuccessListener {
//                it.storage.downloadUrl.addOnSuccessListener { image ->
//                    storeData(image.toString())
//                }
//            }
//            .addOnFailureListener {
//                dialog.dismiss()
//                Toast.makeText(requireContext(),"Something went wrong with storage.", Toast.LENGTH_SHORT).show()
//
//            }
//
//    }

}