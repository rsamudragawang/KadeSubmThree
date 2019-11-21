package com.ganargatul.submthree.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ganargatul.submthree.R
import com.ganargatul.submthree.model.PastEventItems
import com.squareup.picasso.Picasso

class PastEventAdapter(private  val context: Context, private val Items: List<PastEventItems>, private val listener: (PastEventItems) -> Unit): RecyclerView.Adapter<PastEventAdapter.ViewHolder>() {
    class ViewHolder(view : View) :RecyclerView.ViewHolder(view) {
        private val img = view.findViewById<ImageView>(R.id.image_items)
        private val name = view.findViewById<TextView>(R.id.title_items)

        fun bindItem(items: PastEventItems, listener: (PastEventItems) -> Unit){
            name.text = items.strEvent
            items.strThumb?.let { Picasso.get().load(it).into(img) }
            itemView.setOnClickListener { listener(items) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.league_items,parent,false))
    }

    override fun getItemCount(): Int = Items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(Items[position],listener)
    }

}