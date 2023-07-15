package com.example.chadhatimes

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.chadhatimes.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private var Country="in"
private var Category="general"
class MainActivity : AppCompatActivity() {
    private lateinit var adapter:NewsAdapter
    val category=arrayOf("Business","Entertainment","General","Health","Science","Sports","Technology")
    val country=arrayOf("India","USA","Japan","China","Russia","Germany","Mexico","France","Brazil","Argentina","Canada","Australia")
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getNews()
        val spinner=findViewById<Spinner>(R.id.spinner)
        val arrayAdapter= ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,country)
        spinner.adapter=arrayAdapter
        spinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item:String=country[position]
                when(country[position]){
                    "India"->{Country="in" }
                    "USA"->{Country="us"}
                    "China"->{Country="ch"}
                    "Russia"->{Country="ru"}
                    "Germany"->{Country="gr"}
                    "Mexico"->{Country="mx"}
                    "France"->{Country="fr"}
                    "Brazil"->{Country="br"}
                    "Argentina"->{Country="ar"}
                    "Canada"->{Country="ca"}
                    "Australia"->{Country="au"}

                    else-> {
                        Country = "jp"
                    }
                }
                getNews()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Country="in"
            }

        }
       val spinner2=findViewById<Spinner>(R.id.spinner2)
        val arrayAdapter2= ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,category)
        spinner2.adapter=arrayAdapter2
        spinner2.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(category[position]){
                    "Business"->{Category="business"}
                    "Science" ->{Category="science"}
                    "Entertainment" ->{Category="entertainment"}
                    "Health" ->{Category="health"}
                    "Sports" ->{Category="sports"}
                    "Technology" ->{Category="technology"}
                    else-> {Category = "general" }
                }
                getNews()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Category="general"
            }

        }
    }

    private fun getNews() {
        val news=NewsService.newsInstance.getHeadlines(Country, Category)
        news.enqueue(object: Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                val newsList = findViewById<RecyclerView>(R.id.newsList)

                if (news != null) {
                        adapter = NewsAdapter(this@MainActivity, news.articles as ArrayList<Article>)
                        newsList.adapter = adapter
                }

                val layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                newsList.layoutManager = layoutManager
            }
            override fun onFailure(call:Call<News>,t:Throwable)
            {
                val context: Context =applicationContext
                Toast.makeText(context,"Error while opening please try again after sometime !!",Toast.LENGTH_SHORT).show()
            }

        })
    }
}