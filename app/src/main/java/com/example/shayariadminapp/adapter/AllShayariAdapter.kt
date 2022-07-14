package com.example.shayariadminapp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shayariadminapp.AllShayariActivity
import com.example.shayariadminapp.R
import com.example.shayariadminapp.databinding.ItemShayariBinding
import com.example.shayariadminapp.model.ShayariModel
import com.google.firebase.firestore.FirebaseFirestore


// making adapter to render all shayari from firebase

class AllShayariAdapter(
    val allShayariActivity: AllShayariActivity,
    val shayariList: ArrayList<ShayariModel>,
    val categoryId: String
) :RecyclerView.Adapter<AllShayariAdapter.ShayariViewHolder>(){
    val db = FirebaseFirestore.getInstance()
    // implementing all members
    // added shayariViewHolder class
    class ShayariViewHolder(val binding: ItemShayariBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShayariViewHolder {
        // binding ShayariViewHolder with ItemShayariBinding
        return  ShayariViewHolder(ItemShayariBinding.inflate(LayoutInflater.from(parent.context),parent,false))


    }

    override fun onBindViewHolder(holder: ShayariViewHolder, position: Int) {


        holder.binding.itemShayari.text = shayariList[position].data.toString()

        holder.binding.btnDelete.setOnClickListener{
            db.collection("Shayari")
                .document(categoryId)
                .collection("all")
                .document(shayariList[position].id!!)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(allShayariActivity,"Deleted Successfully",Toast.LENGTH_SHORT).show()
                }
        }

    }

    override fun getItemCount() = shayariList.size
}