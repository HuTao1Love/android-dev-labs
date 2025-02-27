package ru.hutao.shop.presentation.productDetailActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import ru.hutao.shop.R
import ru.hutao.shop.data.models.CartItem
import ru.hutao.shop.data.models.Product
import ru.hutao.shop.data.repositories.LocalCartRepository
import ru.hutao.shop.databinding.ActivityProductDetailBinding
import ru.hutao.shop.presentation.cartActivity.CartActivity
import ru.hutao.shop.presentation.mainActivity.MainActivity
import java.text.DateFormat
import java.text.SimpleDateFormat

class ProductDetailActivity : AppCompatActivity() {
    private val dateFormat: DateFormat = SimpleDateFormat.getDateInstance()

    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var binding: ActivityProductDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ProductDetailViewModel(LocalCartRepository)
        val product = intent.getSerializableExtra("product", Product::class.java)!!

        product.let {
            binding.productName.text = it.name
            binding.productDescription.text = it.description
            binding.productPrice.text = "${it.price} ₽"
            binding.productCategory.text = "Категория: ${it.category.name}"
            binding.productCreatedAt.text = "Создано: ${dateFormat.format(it.createdAt)}"

            Glide
                .with(this)
                .load(it.image)
                .into(binding.productImage)
        }

        binding.productCategory.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).apply {
                putExtra("searchQuery", "category:${product.category.name}")
            })
        }

        binding.cart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        binding.btnDecrease.setOnClickListener {
            lifecycleScope.launch {
                viewModel.processIntent(ProductDetailIntent.UpdateCartItemQuantity(product, (viewModel.quantity ?: 0) - 1))
            }
        }
        binding.btnIncrease.setOnClickListener {
            lifecycleScope.launch {
                viewModel.processIntent(ProductDetailIntent.UpdateCartItemQuantity(product, (viewModel.quantity ?: 0) + 1))
            }
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is ProductDetailState.Error -> showError(state.message)
                    is ProductDetailState.Loading -> showLoading()
                    is ProductDetailState.Success -> showDetails(state.item)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.processIntent(ProductDetailIntent.LoadProduct(product.id))
        }
    }

    private fun showLoading() {
        binding.btnDecrease.visibility = View.GONE
        binding.btnIncrease.visibility = View.GONE
    }

    private fun showDetails(item: CartItem?) {
        binding.btnDecrease.visibility = View.VISIBLE
        binding.btnIncrease.visibility = View.VISIBLE
        binding.cartQuantity.text = (item?.quantity ?: 0).toString()
    }

    private fun showError(message: String) {
        Toast.makeText(this, "Ошибка: $message", Toast.LENGTH_SHORT).show()
    }
}