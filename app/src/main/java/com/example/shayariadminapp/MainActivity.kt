package com.example.shayariadminapp

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shayariadminapp.adapter.CategoryAdaptor
import com.example.shayariadminapp.databinding.ActivityMainBinding
import com.example.shayariadminapp.databinding.DialogAddCategoryBinding
import com.example.shayariadminapp.model.CategoryModel
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    // to bind the screen
    private lateinit var binding: ActivityMainBinding
    // to get data from FireBase
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        db = FirebaseFirestore.getInstance()

        binding.btnAddCat.setOnClickListener{
            val addCatDialog = Dialog(this@MainActivity)
            val  binding = DialogAddCategoryBinding.inflate(layoutInflater)
            addCatDialog.setContentView(binding.root)

            if(addCatDialog.window !=null){
                addCatDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            }

            binding.dialogBtnAddCat.setOnClickListener{
                val name = binding.addCat.text.toString()
                val id = db.collection("Shayari").document().id
                val data = CategoryModel(id,name)
                db.collection("Shayari").document(id).set(data).addOnSuccessListener {
                    Toast.makeText(this@MainActivity,"Add",Toast.LENGTH_SHORT).show()
                    addCatDialog.dismiss()
                }.addOnCanceledListener {
                    Toast.makeText(this@MainActivity, " $it",Toast.LENGTH_SHORT).show()
                }
            }
            addCatDialog.show()
        }

        db.collection("Shayari").addSnapshotListener{ value,error ->
            val shayari = arrayListOf<CategoryModel>()
            val data = value?.toObjects(CategoryModel::class.java)
            shayari.addAll(data!!)
            binding.recyclerViewCategory.layoutManager = LinearLayoutManager(this)
            binding.recyclerViewCategory.adapter = CategoryAdaptor(this,shayari)
        }
    }
}