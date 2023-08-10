package com.example.newsapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.DetailNewsActivity
import com.example.newsapp.R
import com.example.newsapp.model.NewsModel
import com.example.newsapp.util.formatTimeAgo


@SuppressLint("NotifyDataSetChanged")
class NewsAdapter (var context: Context) : RecyclerView.Adapter<NewsAdapter.NewsHolder>(),
    Filterable {
    var list: ArrayList<NewsModel.ArticlesEntity> = ArrayList()
    var listFilter: ArrayList<NewsModel.ArticlesEntity> = ArrayList()

    init {
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPost(list: ArrayList<NewsModel.ArticlesEntity>) {
        this.list = list
        listFilter = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(
            LayoutInflater.from(context).inflate(R.layout.item_list_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        var modelList:NewsModel.ArticlesEntity = listFilter[position]
        holder.txtSourceName!!.text = modelList.source!!.name
        holder.txtDesc!!.text = modelList.description
        holder.txtNewsName!!.text = modelList.author.toString()
        holder.txtDate!!.text = formatTimeAgo(modelList.publishedAt.toString())
        Glide.with(context).load(modelList.urlToImage.toString()).into(holder.gambar!!)

        holder.itemView.setOnClickListener {
            var pindah = Intent(context, DetailNewsActivity::class.java)
            pindah.putExtra("name", modelList.title)
            pindah.putExtra("url", modelList.url)
            context.startActivity(pindah)
        }
        holder.imgShare!!.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, modelList.url)
            sendIntent.type = "text/plain"
            context.startActivity(sendIntent)
        }
    }

    override fun getItemCount(): Int {
        return listFilter.size
    }

    inner class NewsHolder(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    ) {
        var txtSourceName: TextView = itemView!!.findViewById(R.id.iv_source_news)
        var txtDesc: TextView? = itemView!!.findViewById(R.id.tv_articleTitle)
        var txtNewsName: TextView? = itemView!!.findViewById(R.id.tv_news_name)
        var txtDate: TextView? = itemView!!.findViewById(R.id.tv_articleDateTime)
        var imgShare: AppCompatImageButton? = itemView!!.findViewById(R.id.iv_share_news)
        var gambar: ImageView? = itemView!!.findViewById(R.id.articleImage)

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) listFilter = list else {
                    val filteredList = ArrayList<NewsModel.ArticlesEntity>()
                    list
                        .filter {
                            (it.title!!.contains(constraint!!))

                        }
                        .forEach { filteredList.add(it) }
                    listFilter = filteredList

                }
                return FilterResults().apply { values = listFilter }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                listFilter = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<NewsModel.ArticlesEntity>
                notifyDataSetChanged()
            }
        }
    }
}