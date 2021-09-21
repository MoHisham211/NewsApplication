package mo.zain.newsapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mo.zain.newsapplication.R
import mo.zain.newsapplication.model.Article
import mo.zain.newsapplication.model.NewsModel
import mo.zain.newsapplication.ui.DetailsActivity
import java.io.Serializable

class NewsAdapter(var newsModels: NewsModel, var context: Context) :
    RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() , Serializable {

    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var Image: ImageView
        var sourceName: TextView
        var time: TextView
        var title: TextView

        init {
            Image= itemView.findViewById(R.id.Image)
             sourceName= itemView.findViewById(R.id.sourceName)
             time= itemView.findViewById(R.id.time)
             title = itemView.findViewById(R.id.title)
            itemView.setOnClickListener(View.OnClickListener {
                val intent:Intent = Intent(context, DetailsActivity::class.java)
                //Bundle mBundle = new Bundle();
                intent.putExtra(
                    "CHOOSE",
                    newsModels.articles?.get(adapterPosition)
                )
                context.startActivity(intent)
            })
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.news_items,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return newsModels.articles.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val newsModel = newsModels
        holder.itemView.apply {
            Glide.with(context).load(newsModels.articles[position].urlToImage).into(holder.Image)
            holder.sourceName.text = newsModels.articles[position].source.name
            holder.time.text = newsModels.articles[position].publishedAt
            holder.title.text = newsModels.articles[position].title

        }
    }

}