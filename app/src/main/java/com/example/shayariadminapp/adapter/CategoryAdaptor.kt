package com.example.shayariadminapp.adapter


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shayariadminapp.MainActivity
import com.example.shayariadminapp.databinding.ItemCategoryBinding
import com.example.shayariadminapp.model.CategoryModel
import com.google.firebase.firestore.FirebaseFirestore

class CategoryAdaptor(val mainActivity: MainActivity, val shayari: ArrayList<CategoryModel>) : RecyclerView.Adapter<CategoryAdaptor.CatViewHolder>(){
    class CatViewHolder( val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    val db = FirebaseFirestore.getInstance()

    val colorsList = arrayListOf<String>("#55efc4","#74b9ff","#d63031","#e17055","#9b59b6")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {

        if(position%5==0){
            holder.binding.itemText.setBackgroundColor(Color.parseColor(colorsList[0]))
        }else if(position%5==1){
            holder.binding.itemText.setBackgroundColor(Color.parseColor(colorsList[1]))
        }else if(position%5==2){
            holder.binding.itemText.setBackgroundColor(Color.parseColor(colorsList[2]))
        }else if(position%5==3){
            holder.binding.itemText.setBackgroundColor(Color.parseColor(colorsList[3]))
        }else if(position%5==4){
            holder.binding.itemText.setBackgroundColor(Color.parseColor(colorsList[4]))
        }

        holder.binding.btnDelete.setOnClickListener{
            db.collection("Shayari").document(shayari[position].id!!).delete().addOnSuccessListener {
                Toast.makeText(mainActivity,"Category deleted succesfully",Toast.LENGTH_SHORT).show()
            }
        }

        holder.binding.itemText.text = shayari[position].name.toString()
        holder.binding.root.setOnClickListener{
//            val intent = Intent(mainActivity,AllShayariActivity::class.java)
//            intent.putExtra("id",shayari[position].id)
//            intent.putExtra("name",shayari[position].name)
//            mainActivity.startActivity(intent)
        }
    }

    override fun getItemCount() = shayari.size
}