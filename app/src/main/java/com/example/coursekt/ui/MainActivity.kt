package com.example.coursekt

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.coursekt.databinding.ActivityMainBinding
import com.example.coursekt.domain.PersonDataResponse
import com.example.coursekt.domain.RetrofitHelper
import com.example.coursekt.ui.viewmodels.MainActivityViewModel
import com.example.coursekt.ui.states.MainUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()
    //private val viewModel: MainActivityViewModel by viewModels()
    val retrofit = RetrofitHelper.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* Course Corauntine */
        lifecycleScope.launch(Dispatchers.IO) {
            coroutinesExample()
        }


    }

    suspend fun coroutinesExample() {
        val response: Response<PersonDataResponse> = retrofit.getSuperHero()
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                val dataPerson  = response.body()
                Toast.makeText(this@MainActivity, "${response.isSuccessful} - ${response.message()}", Toast.LENGTH_SHORT).show()
                Toast.makeText(this@MainActivity, "${response.isSuccessful} - ${response.body()}", Toast.LENGTH_SHORT).show()
                binding.tvTitle.text = dataPerson?.description
                binding.progressBar.visibility = View.GONE
                Log.e("response", "body::${response.body()}")
                Log.e("response", "isSuccessful::${response.isSuccessful}")
                Log.e("response", "id::${dataPerson?.id}")
                Log.e("response", "name::${dataPerson?.name}")
                Log.e("response", "image::${dataPerson?.image}")
            }
        }

    }

    fun stateFlowExample() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when(uiState) {
                        is MainUIState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.tvTitle.text = "Error: ${uiState.message}"
                        }
                        MainUIState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is MainUIState.Success -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.tvTitle.text = "Likes: ${uiState.likes}"
                        }
                    }
                }
            }
        }
        viewModel.example3()
    }

}