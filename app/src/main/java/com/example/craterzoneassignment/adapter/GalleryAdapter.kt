package com.example.craterzoneassignment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.craterzoneassignment.R
import com.example.craterzoneassignment.models.Photo
import com.example.craterzoneassignment.utils.ImageHelper
import com.example.craterzoneassignment.utils.ImageHelper.createImageUrl
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class GalleryAdapter (var iImageLoadCallback: IImageLoadCallback): RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    private val imagesList = ArrayList<Photo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_grid, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = imagesList[position]

        createImageUrl(photo)?.let {
            Picasso.get().load(it).centerCrop().resize(600, 400).error(R.drawable.ic_no_img)
                .into(holder.imagePhoto, object : Callback {
                    override fun onSuccess() {
                        iImageLoadCallback.onVisible(true)
                    }

                    override fun onError(e: Exception?) {
                        iImageLoadCallback.onVisible(false)
                    }
                })
        }
    }

    fun setData(listPhoto: List<Photo>) {
        val diffCallback = ImageDiffCallback(imagesList, listPhoto)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        imagesList.clear()
        imagesList.addAll(listPhoto)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setDataOne(listPhoto: List<Photo>) {
        imagesList.clear()
        imagesList.addAll(listPhoto)
        notifyDataSetChanged()
    }

    fun addAll(listPhoto: List<Photo>) {
        for (result in listPhoto) {
            add(result)
        }
    }

    private fun add(photo: Photo) {
        imagesList.add(photo)
        notifyItemInserted(imagesList.size- 1)
    }

    fun getImageList() : ArrayList<Photo> {
        return imagesList
    }

    private fun updateImage(imageView: ImageView) {
        ImageHelper.updateImageCorners(imageView, 6)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imagePhoto: ImageView = itemView.findViewById(R.id.image_photo)
    }

    interface IImageLoadCallback {
        fun onVisible(visible: Boolean)
    }
}