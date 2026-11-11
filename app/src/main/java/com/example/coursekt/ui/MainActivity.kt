package com.example.coursekt

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.coursekt.databinding.ActivityMainBinding
import com.example.coursekt.ui.MainActivityViewModel
import com.example.coursekt.ui.MainUIState
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                            binding.progressBar.visibility = View.GONE
                            binding.tvTitle.text = "Likes: ${uiState.likes}"
                        }
                    }
                }
            }
        }

    }
}