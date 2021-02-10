package fr.isen.herbault.category

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.herbault.HomeActivity
import fr.isen.herbault.R
import fr.isen.herbault.category.CategoryAdapter
import fr.isen.herbault.databinding.ActivityCategoryBinding
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.herbault.network.MenuResult
import fr.isen.herbault.network.NetworkConstant
import com.google.gson.GsonBuilder
import fr.isen.herbault.BaseActivity
import fr.isen.herbault.detail.DetailActivity
import fr.isen.herbault.network.Dish



import org.json.JSONObject

enum class ItemType { ENTREE,PLAT,DESSERT;

    companion object {
        fun categoryTitle(item: ItemType?) : String {
            return when(item) {
                ENTREE -> "Entrées"
                PLAT -> "Plats"
                DESSERT -> "Desserts"
                else -> ""
            }
        }
    }
}



class CategoryActivity : BaseActivity() {
    private lateinit var bindind: ActivityCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindind = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(bindind.root)
        val selectedItem = intent.getSerializableExtra(HomeActivity.CATEGORY_NAME) as? ItemType
        bindind.categorytitle.text = getCategoryTitle(selectedItem)

        loadList(listOf<Dish>())
        makeRequest(selectedItem)
        Log.d("lifecycle", "onCreate")

    }



    private fun makeRequest(selectedItem: ItemType?) {
        resultFromCache()?.let {
            parseResult(it, selectedItem)
        } ?: run {

            val loader = fr.isen.herbault.loader.Loader()
            loader.show(this, "récupération du menu")

            val queue = Volley.newRequestQueue(this)
            val url = NetworkConstant.BASE_URL + NetworkConstant.PATH_MENU
            val jsonData = JSONObject()

            jsonData.put(NetworkConstant.ID_SHOP, "1")

            val request = JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonData,
                { response ->
                    loader.hide(this)
                    cacheResult(response.toString())
                    parseResult(response.toString(), selectedItem)

                },
                { error ->
                    loader.hide(this)
                    error.message?.let {
                        Log.d("request", it)
                    } ?: run {
                        Log.d("request", error.toString())
                    }
                }
            )

            queue.add(request)
        }
    }

    private fun loadList(dishes: List<Dish>?) {
        dishes?.let {
            val adapter = CategoryAdapter(it) { dish ->
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra( DetailActivity.DISH_EXTRA, dish)
                startActivity(intent)
            }
            bindind.recyclerView.layoutManager = LinearLayoutManager(this)
            bindind.recyclerView.adapter = adapter
        }
    }

    private fun parseResult(response: String, selectedItem: ItemType?) {
        val menuResult = GsonBuilder().create().fromJson(response, MenuResult::class.java)
        val items = menuResult.data.firstOrNull { it.name == ItemType.categoryTitle(selectedItem) }
        loadList(items?.items)
    }

    private fun getCategoryTitle(item: ItemType?): String {
        return when(item) {
            ItemType.ENTREE -> getString(R.string.txtEntrees)
            ItemType.PLAT -> getString(R.string.txtPlats)
            ItemType.DESSERT -> getString(R.string.txtDesserts)
            else -> ""
        }
    }

    private fun cacheResult(response: String) {
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(REQUEST_CACHE, response)
        editor.apply()
    }

    private fun resultFromCache(): String? {
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(REQUEST_CACHE, null)
    }
    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "onResume")
    }
    override fun onRestart() {
        super.onRestart()
        Log.d("lifecycle", "onRestart")
    }
    override fun onDestroy() {
        Log.d("lifecycle", "onDestroy")
        super.onDestroy()
    }

    companion object {
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
        const val REQUEST_CACHE = "REQUEST_CACHE"
    }
}
