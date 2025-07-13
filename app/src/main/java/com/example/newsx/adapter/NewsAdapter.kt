package com.example.newsx.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsx.databinding.ItemArticleBinding
import com.example.newsx.models.Article

class NewsAdapter(private val articles: List<Article>, private val onItemClick:(Article)->Unit) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.binding.title.text = article.title
        holder.binding.description.text = article.description

        Glide.with(holder.itemView.context)
            .load(article.urlToImage)
            .into(holder.binding.image)

        holder.binding.root.setOnClickListener{
onItemClick(article)
        }
    }

    override fun getItemCount(): Int = articles.size
}
