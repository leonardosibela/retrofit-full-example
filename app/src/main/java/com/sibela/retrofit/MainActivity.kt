package com.sibela.retrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sibela.retrofit.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        lifecycleScope.launchWhenStarted {
            binding.progressbar.isVisible = true
            val response = try {
                RetrofitInstance.api.getTodos()
            } catch (ioe: IOException) {
                Log.e(TAG, "You might not have internet connection")
                return@launchWhenStarted
            } catch (httpe: HttpException) {
                Log.e(TAG, "HttpException, unexpected response")
                return@launchWhenStarted
            }

            if (response.isSuccessful && response.body() != null) {
                todoAdapter.todoList = response.body()!!
            } else {
                Log.e(TAG, "Response not successful")
            }
            binding.progressbar.isVisible = false
        }
    }

    private fun setupRecyclerView() = binding.rvTodos.apply {
        todoAdapter = TodoAdapter()
        adapter = todoAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}