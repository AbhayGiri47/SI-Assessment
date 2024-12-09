package com.example.siassessment.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.siassessment.utils.CustomProgressBar
import javax.inject.Inject

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    @Inject
    protected lateinit var customProgressBar: CustomProgressBar

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!


    protected abstract fun getViewBinding(): VB

    abstract fun observeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(binding.root)
        initialize()
        observeViewModel()
    }

    protected abstract fun initialize()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}