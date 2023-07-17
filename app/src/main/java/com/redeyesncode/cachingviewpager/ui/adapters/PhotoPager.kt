package com.redeyesncode.cachingviewpager.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.redeyesncode.cachingviewpager.R
import com.redeyesncode.cachingviewpager.data.ResponseMarsPhotos
import com.redeyesncode.cachingviewpager.databinding.ActivityMainBinding
import com.redeyesncode.cachingviewpager.databinding.ItemPageBinding

class PhotoPager(var context: Context, var arrayList: ArrayList<ResponseMarsPhotos>): RecyclerView.Adapter<PhotoPager.ViewHolder>() {
    lateinit var binding: ItemPageBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemPageBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = arrayList[position]

        Glide.with(context).load(image.imgSrc.toString()).placeholder(R.drawable.ic_loadin).into(binding.ivMars)


    }

    override fun getItemCount(): Int {
        // Return the number of items in your ViewPager2
        return arrayList.size
    }

    inner class ViewHolder(binding: ItemPageBinding) : RecyclerView.ViewHolder(binding.root)
}