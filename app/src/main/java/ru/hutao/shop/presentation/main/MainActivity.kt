package ru.hutao.shop.presentation.main

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.hutao.shop.R
import ru.hutao.shop.data.models.Product
import ru.hutao.shop.data.repositories.ProductRepository
import ru.hutao.shop.usecases.GetProductsUseCase
import ru.hutao.shop.usecases.SearchProductsUseCase

// профиль
class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchView: SearchView
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        searchView = findViewById(R.id.search)
        adapter = ProductAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val repository = ProductRepository()
        viewModel = MainViewModel(GetProductsUseCase(repository), SearchProductsUseCase(repository))

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is MainState.Loading -> {
                        showLoading()
                    }
                    is MainState.Success -> {
                        showProducts(state.products)
                    }
                    is MainState.Error -> {
                        showError(state.message)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.processIntent(MainIntent.LoadProducts)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                lifecycleScope.launch {
                    query?.let {
                        viewModel.processIntent(MainIntent.SearchProducts(query))
                    }
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch {
                    newText?.let {
                        viewModel.processIntent(MainIntent.SearchProducts(newText))
                    }
                }

                return false
            }
        })
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
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

