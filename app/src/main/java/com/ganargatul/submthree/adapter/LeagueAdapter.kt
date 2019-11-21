package com.ganargatul.submthree.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ganargatul.submthree.R
import com.ganargatul.submthree.model.LeagueItems
import com.squareup.picasso.Picasso

class LeagueAdapter(private  val context: Context, private val Items: List<LeagueItems>, private val listener: (LeagueItems) -> Unit): RecyclerView.Adapter<LeagueAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.league_items,parent,false))
    }

    override fun getItemCount(): Int = Items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(Items[position],listener)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val img = view.findViewById<ImageView>(R.id.image_items)
        private val name = view.findViewById<TextView>(R.id.title_items)

        fun bindItem(items: LeagueItems,listener: (LeagueItems) -> Unit){
            name.text = items.name
            items.image?.let { Picasso.get().load(it).into(img) }
            itemView.setOnClickListener { listener(items) }
        }
    }
}