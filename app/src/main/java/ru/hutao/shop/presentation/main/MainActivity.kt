package ru.hutao.shop.presentation.main

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.hutao.shop.R
import ru.hutao.shop.data.models.Product
import ru.hutao.shop.data.repositories.ProductRepository
import ru.hutao.shop.usecases.GetProductsUseCase

// поиск (в той же активити), профиль
// озвучка с помощью talkback
class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        adapter = ProductAdapter()

        // Check orientation and set the appropriate LayoutManager
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.layoutManager = GridLayoutManager(this, 3) // 3 items per row in landscape
        } else {
            recyclerView.layoutManager = LinearLayoutManager(this) // Default LinearLayoutManager for portrait
        }
        recyclerView.adapter = adapter

        val repository = ProductRepository()
        val useCase = GetProductsUseCase(repository)
        viewModel = MainViewModel(useCase)

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
