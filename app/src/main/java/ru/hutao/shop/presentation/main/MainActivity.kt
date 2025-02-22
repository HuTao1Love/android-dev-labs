package ru.hutao.shop.presentation.main

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.hutao.shop.R
import ru.hutao.shop.data.models.Product
import ru.hutao.shop.data.repositories.ProductRepository

// профиль
class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchView: SearchView
    private lateinit var adapter: ProductAdapter
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        searchView = findViewById(R.id.search)
        adapter = ProductAdapter()

        recyclerView.adapter = adapter

        val repository = ProductRepository()
        viewModel = MainViewModel(repository)

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is MainState.Loading -> showLoading()
                    is MainState.Success -> showProducts(state.products)
                    is MainState.Error -> showError(state.message)
                }
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    query.let { viewModel.processIntent(searchProducts(query)) }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    newText.let { viewModel.processIntent(searchProducts(newText)) }
                }
                return false
            }
        })

        lifecycleScope.launch {
            searchView.setQuery(intent.getStringExtra("searchQuery"), true)
        }
    }

    private fun searchProducts(query: String?): MainIntent {
        return when {
            query == null
                || query == ""
                || query.equals("category:", true) -> MainIntent.LoadProducts
            query != "category:"
                && query.startsWith("category:", true) -> MainIntent.SearchProductsCategory(query.replace("category:", ""))
            else -> MainIntent.SearchProducts(query)
        }
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun showProducts(products: List<Product>) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        adapter.submitList(products)
    }

    private fun showError(message: String) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        Toast.makeText(this, "Ошибка: $message", Toast.LENGTH_SHORT).show()
    }
}

