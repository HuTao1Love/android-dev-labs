package ru.hutao.shop.presentation.cartActivity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.hutao.shop.R
import ru.hutao.shop.data.models.CartItem
import ru.hutao.shop.data.repositories.LocalCartRepository

class CartActivity : ComponentActivity(), ICartItemChangedListener {
    private lateinit var viewModel: CartViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = CartAdapter(this)

        recyclerView.adapter = adapter

        viewModel = CartViewModel(LocalCartRepository)

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is CartState.Loading -> showLoading()
                    is CartState.Success -> showCart(state.items)
                    is CartState.Error -> showError(state.message)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.processIntent(CartIntent.LoadCart)
        }
    }

    private fun showLoading() { }

    private fun showCart(items: List<CartItem>) {
        recyclerView.visibility = View.VISIBLE
        adapter.submitList(items)
    }

    private fun showError(message: String) {
        recyclerView.visibility = View.GONE
        Toast.makeText(this, "Ошибка: $message", Toast.LENGTH_SHORT).show()
    }

    override fun onRemoveClicked(cartItem: CartItem) {
        lifecycleScope.launch {
            viewModel.processIntent(CartIntent.RemoveFromCart(cartItem))
        }
    }

    override fun onDecreaseClicked(cartItem: CartItem) {
        lifecycleScope.launch {
            viewModel.processIntent(
                CartIntent.UpdateCartItemQuantity(
                    cartItem.product,
                    cartItem.quantity - 1))
        }
    }

    override fun onIncreaseClicked(cartItem: CartItem) {
        lifecycleScope.launch {
            viewModel.processIntent(
                CartIntent.UpdateCartItemQuantity(
                    cartItem.product,
                    cartItem.quantity + 1))
        }
    }
}