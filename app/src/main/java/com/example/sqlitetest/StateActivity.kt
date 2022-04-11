package com.example.sqlitetest


import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.example.sqlitetest.databinding.ActivityStateBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_state.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class StateActivity : AppCompatActivity() {
    var bookList = ArrayList<Book>()

    private var _binding: ActivityStateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state)
        _binding = ActivityStateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = DBHelper(this)
        bookList = database.getAllBooks()


        binding.btnLogin.setOnClickListener {
            for (i in 0 until bookList.size) {
                if (etUsername.text.toString() == bookList[i].bookName && etPassword.text.toString() == bookList[i].authorName) {
                    viewModel.login()
                    break
                }else {
                    viewModel.loginerror()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.loginUiState.collect {
                when (it) {
                    is StateViewModel.LoginUiState.Success -> {
                        Snackbar.make(
                            binding.root,
                            "ログイン成功！",
                            Snackbar.LENGTH_LONG
                        ).show()
                        binding.progressBar.isVisible = false
                    }
                    is StateViewModel.LoginUiState.Error -> {
                        Snackbar.make(
                            binding.root,
                            it.message,
                            Snackbar.LENGTH_LONG
                        ).show()
                        binding.progressBar.isVisible = false
                    }
                    is StateViewModel.LoginUiState.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}