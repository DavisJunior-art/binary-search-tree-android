package com.example.arvorebinariabusca

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.arvorebinariabusca.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: TreeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.treeOutput.typeface = android.graphics.Typeface.MONOSPACE
        binding.insertButton.setOnClickListener { insertValue() }
        binding.searchButton.setOnClickListener { searchValue() }
        binding.resetButton.setOnClickListener { viewModel.resetChallenge() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.treeOutput.text = state.treeText
                    binding.insertedValues.text = getString(R.string.inserted_values_format, state.insertedValues)
                    binding.sortedValues.text = getString(R.string.sorted_values_format, state.sortedValues)
                    binding.statusMessage.text = state.message
                }
            }
        }
    }

    private fun insertValue() {
        val value = binding.valueInput.text.toString().trim().toIntOrNull()
        if (value == null) {
            binding.valueInput.error = getString(R.string.enter_valid_integer)
            return
        }
        binding.valueInput.error = null
        viewModel.insert(value)
        binding.valueInput.text?.clear()
    }

    private fun searchValue() {
        val value = binding.searchInput.text.toString().trim().toIntOrNull()
        if (value == null) {
            binding.searchInput.error = getString(R.string.enter_valid_integer)
            return
        }
        binding.searchInput.error = null
        viewModel.search(value)
    }
}
