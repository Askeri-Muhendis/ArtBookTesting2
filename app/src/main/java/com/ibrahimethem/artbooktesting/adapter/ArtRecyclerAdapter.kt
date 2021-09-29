package com.ibrahimethem.artbooktesting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ibrahimethem.artbooktesting.R
import com.ibrahimethem.artbooktesting.roomdb.Art
import javax.inject.Inject

class ArtRecyclerAdapter @Inject constructor(
    val glide : RequestManager
) : RecyclerView.Adapter<ArtRecyclerAdapter.ArtViewHolder>(){
    class ArtViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    /** DiffUtil ile listemizde değişen öğelerin hangileri olduğunu görüp onları değiştirebiliyoruz
     *  bu işlem asenkron olarak arka planda çok hızlı çalışıyor bunun için diffUtil'i kullanıcaz
     * */

    private val diffUtil = object : DiffUtil.ItemCallback<Art>(){
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
            //-> veriler eski mi yeni mi kontrol ediyoruz boolean değerine göre işlem yapıyoruz
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)


    //listemiz için
    var arts : List<Art>
        get() = recyclerListDiffer.currentList //güncel listeyi alıcak
        set(value) = recyclerListDiffer.submitList(value) //yeni listeyi verecek

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.art_row,parent,false)
        return ArtViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.artRowImageView)
        val nameText = holder.itemView.findViewById<TextView>(R.id.artRowNameText)
        val artistText = holder.itemView.findViewById<TextView>(R.id.artRowArtistText)
        val yearText = holder.itemView.findViewById<TextView>(R.id.artRowYearText)

        //konuma göre art'ı alıyoruz
        val art = arts[position]

        holder.itemView.apply {
            nameText.text = "Name : ${art.name}"
            artistText.text = "Artist : ${art.artistName}"
            yearText.text = "Year : ${art.year}"
            glide.load(art.url).into(imageView)

        }
    }

    override fun getItemCount(): Int {
        return arts.size
    }

}