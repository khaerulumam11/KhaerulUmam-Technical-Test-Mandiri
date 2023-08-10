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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.NewsBySourcesActivity
import com.example.newsapp.R
import com.example.newsapp.model.SourceNewsModel

@SuppressLint("NotifyDataSetChanged")
class SourceNewsAdapter (var context: Context) : RecyclerView.Adapter<SourceNewsAdapter.SourceNewsHolder>(),
    Filterable {
    var list: ArrayList<SourceNewsModel.SourcesEntity> = ArrayList()
    var listFilter: ArrayList<SourceNewsModel.SourcesEntity> = ArrayList()

    init {
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPost(list: ArrayList<SourceNewsModel.SourcesEntity>) {
        this.list = list
        listFilter = list
        notifyDataSetChanged()
    }

//    fun clearItems(){
//        list.clear()
//        listFilter.clear()
//        notifyDataSetChanged()
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceNewsHolder {
        return SourceNewsHolder(
            LayoutInflater.from(context).inflate(R.layout.item_list_source_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SourceNewsHolder, position: Int) {
        var modelList:SourceNewsModel.SourcesEntity = listFilter[position]
        holder.txtName!!.text = modelList.name
        holder.txtDesc!!.text = modelList.description
        holder.txtCategory!!.text = modelList.category.toString()

        holder.itemView.setOnClickListener {
            var pindah = Intent(context, NewsBySourcesActivity::class.java)
            pindah.putExtra("name", modelList.name)
            pindah.putExtra("id", modelList.id)
            context.startActivity(pindah)
        }
    }

    override fun getItemCount(): Int {
        return listFilter.size
    }

    inner class SourceNewsHolder(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    ) {
        var txtName: TextView = itemView!!.findViewById(R.id.tv_source_name)
        var txtDesc: TextView? = itemView!!.findViewById(R.id.tv_description_source)
        var txtCategory: TextView? = itemView!!.findViewById(R.id.tv_category_source)
        var gambar: ImageView? = itemView!!.findViewById(R.id.iv_source)

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) listFilter = list else {
                    val filteredList = ArrayList<SourceNewsModel.SourcesEntity>()
                    list
                        .filter {
                            (it.id!!.contains(constraint!!)) or
                                    (it.name!!.contains(constraint))

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
                    results.values as ArrayList<SourceNewsModel.SourcesEntity>
                notifyDataSetChanged()
            }
        }
    }
}