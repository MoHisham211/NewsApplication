package mo.zain.newsapplication.api

import mo.zain.newsapplication.model.NewsModel
import mo.zain.newsapplication.util.Constant
import retrofit2.Call
import retrofit2.http.GET

interface NewsApi {
    @GET(Constant.END_POINT)
    fun getAllData():Call<NewsModel>
}