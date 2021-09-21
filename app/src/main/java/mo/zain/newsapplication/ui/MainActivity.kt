package mo.zain.newsapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mo.zain.newsapplication.R
import mo.zain.newsapplication.adapter.NewsAdapter
import mo.zain.newsapplication.api.NewsApi
import mo.zain.newsapplication.model.Article
import mo.zain.newsapplication.model.NewsModel
import mo.zain.newsapplication.util.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList
import java.util.Locale.filter

class MainActivity : AppCompatActivity() {

    private var layoutManager:RecyclerView.LayoutManager?=null
    private var adapter:RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>?=null
    private var recyclerView:RecyclerView?=null
    private var progressBar:ProgressBar?=null
    private var searchET:EditText?=null
    private var newsModel:NewsModel?=null
    private var articles: List<Article>?=null
    private val articleListFilter: MutableList<Article> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView =findViewById(R.id.rv)
        progressBar=findViewById(R.id.progressBar)
        searchET=findViewById(R.id.searchET);

        layoutManager=LinearLayoutManager(this)

        recyclerView?.layoutManager=layoutManager


        searchET?.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                articleListFilter.clear()
                if (s.toString().isEmpty()) {
                    getData()
                } else {
                    FilterData(s.toString())
                }
            }

        })
        getData()
    }

    private fun FilterData(text: String) {
        for (articless in articles!!) {
            if (articless.title.contains(text)) {
                articleListFilter.add(articless)
                newsModel?.articles=articleListFilter
            }
        }
        adapter=NewsAdapter(newsModel!!,this@MainActivity)
        recyclerView?.adapter=adapter
        adapter?.notifyDataSetChanged()
    }

    fun getData(){
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit=Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).build()

        val api=retrofit.create(NewsApi::class.java)

        api.getAllData().enqueue(object :Callback<NewsModel>{
            override fun onResponse(call: Call<NewsModel>, response: Response<NewsModel>) {
                newsModel= response.body()!!
                articles=newsModel?.articles
                adapter=NewsAdapter(newsModel!!,this@MainActivity)
                recyclerView?.adapter=adapter
                progressBar?.visibility=View.INVISIBLE
                adapter?.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<NewsModel>, t: Throwable) {
                Toast.makeText(this@MainActivity,""+t.localizedMessage,Toast.LENGTH_SHORT).show()
                progressBar?.visibility=View.INVISIBLE
            }


        })
    }
}