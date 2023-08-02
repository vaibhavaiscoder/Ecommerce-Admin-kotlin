package com.example.ecomadmin.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecomadmin.R
import com.example.ecomadmin.databinding.ItemProductLayoutBinding
import com.example.ecomadmin.model.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ProductAdapter(var context: Context, val list: ArrayList<ProductModel>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = ItemProductLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_product_layout, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.itemProductTextView.text = list[position].productName
        holder.binding.itemProductId.text = list[position].productId
        Glide.with(context).load(list[position].productCoverImg)
            .into(holder.binding.itemProductImageView)


        holder.binding.deleteProduct.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            //set title for alert dialog
            builder.setTitle(R.string.dialogTitle)
            //set message for alert dialog
            builder.setMessage(R.string.dialogMessage)
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            //performing positive action
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                deleteProducts(list[position].productId!!)
            }
            //performing cancel action
            builder.setNeutralButton("Cancel") { dialogInterface, which ->
//                Toast.makeText(context, "clicked cancel\n operation cancel", Toast.LENGTH_LONG)
//                    .show()
            }
            //performing negative action
//            builder.setNegativeButton("No") { dialogInterface, which ->
//                Toast.makeText(context, "clicked No", Toast.LENGTH_LONG).show()
//            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()

        }
    }

fun deleteProducts(doc: String) {
    Firebase.firestore.collection("products")
        .document(doc).delete().addOnSuccessListener {
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
}

override fun getItemCount(): Int {
    return list.size
}
}