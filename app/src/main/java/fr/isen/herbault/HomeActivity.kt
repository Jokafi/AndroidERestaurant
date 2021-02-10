package fr.isen.herbault


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.herbault.category.CategoryActivity
import fr.isen.herbault.category.ItemType
import fr.isen.herbault.databinding.ActivityHomeBinding
import org.json.JSONObject
import com.android.volley.toolbox.Volley.newRequestQueue as newRequestQueue



class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bEntrees.setOnClickListener {
            startCategoryActivity(ItemType.ENTREE)
        }
        binding.bPlats.setOnClickListener {
            startCategoryActivity(ItemType.PLAT)
        }
        binding.bDesserts.setOnClickListener {
            startCategoryActivity(ItemType.DESSERT)
        }
    }
    private fun startCategoryActivity(item: ItemType) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra(CATEGORY_NAME, item)
        startActivity(intent)
    }
    companion object {
        const val CATEGORY_NAME = "CATEGORY_NAME"
    }

}


/*
    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeActivity", "Destroyed")

    }


    fun loadData() {
        val postUrl = "http://test.api.catering.bluecodegames.com/menu"
        val requestQueue = Volley.newRequestQueue(this)

        val postData = JSONObject()
        postData.put("id_shop", "1")

        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, postUrl, postData,
                {
                    Log.d("MainActivity", it.toString())
                },
                {
                    Log.e("MainActivity", it.toString())
                })
        requestQueue.add(jsonObjectRequest)
    } */






