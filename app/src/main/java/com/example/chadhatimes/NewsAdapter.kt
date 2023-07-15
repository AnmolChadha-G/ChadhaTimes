package com.example.chadhatimes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class NewsAdapter(val requireContext: Context,private val articleList:ArrayList<Article>): RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var image:ImageView=itemView.findViewById(R.id.image)
        var des:TextView=itemView.findViewById(R.id.description)
        var head:TextView=itemView.findViewById(R.id.headline)



    }
    fun addArticles(newArticles: List<Article>) {
        articleList.addAll(newArticles)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
       val imageUrl=articleList[position].urlToImage
        Picasso.get().load(imageUrl).into(holder.image)
        holder.des.text=articleList[position].description
        holder.head.text=articleList[position].title

    }

}