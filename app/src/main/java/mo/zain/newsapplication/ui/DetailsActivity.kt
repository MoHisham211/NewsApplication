package mo.zain.newsapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import mo.zain.newsapplication.R
import mo.zain.newsapplication.model.Article

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val i = intent
        val article = i.getSerializableExtra("CHOOSE") as Article?
        var newsImageView: ImageView = findViewById(R.id.newsImageView)
        Glide.with(this)
            .load(article?.urlToImage)
            .into(newsImageView)
        var source: TextView = findViewById(R.id.source)
        source.setText(article?.source!!.name)
        var titletxt: TextView = findViewById(R.id.titletxt)
        titletxt.setText(article.title)
        var destxt: TextView = findViewById(R.id.destxt)
        destxt.setText(article.description)
        var backImage: ImageView? = findViewById(R.id.backImage)
        backImage?.setOnClickListener(View.OnClickListener { finish() })
    }
}