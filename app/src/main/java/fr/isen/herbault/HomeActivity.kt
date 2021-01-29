
package fr.isen.herbault

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.herbault.databinding.ActivityHomeBinding

private lateinit var binding: ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bEntrees.setOnClickListener {
            val toast = Toast.makeText(applicationContext, "Voici les entrées!", Toast.LENGTH_SHORT)
            toast.show()
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }
        binding.bPlats.setOnClickListener {
            val toast = Toast.makeText(applicationContext, "Voici les plats!", Toast.LENGTH_SHORT)
            toast.show()
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }
        binding.bDesserts.setOnClickListener {
            val toast = Toast.makeText(applicationContext, "Voici les desserts!", Toast.LENGTH_SHORT)
            toast.show()
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeActivity", "Destroyed")

    }
}


private lateinit var linearLayoutManager: LinearLayoutManager

linearLayoutManager = LinearLayoutManager(this)
findViewById<RecyclerView>(R.id.recyclerView).layoutManager = LinearLayoutManager
