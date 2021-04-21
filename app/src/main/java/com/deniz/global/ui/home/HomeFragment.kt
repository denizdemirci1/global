package com.deniz.global.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.deniz.global.R
import com.deniz.global.databinding.FragmentHomeBinding
import com.deniz.global.model.response.UserResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author: deniz.demirci
 * @date: 20.04.2021
 */

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    private val args: HomeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCollectors()
        setListeners()
    }

    private fun setListeners() {
        binding.buttonFetch.setOnClickListener {
            viewModel.getUserId(args.nextPath)
        }
    }

    private fun setCollectors() {
        lifecycleScope.launch {
            viewModel.state.collect { homeViewState ->
                when (homeViewState) {
                    is HomeViewState.Initial -> onInitial(homeViewState.fetchCount)
                    HomeViewState.Loading -> onLoading(true)
                    is HomeViewState.Error -> {
                        onLoading(false)
                        onError(homeViewState.message)
                    }
                    is HomeViewState.Success -> {
                        onLoading(false)
                        onSuccess(homeViewState.data, homeViewState.fetchCount)
                    }
                }
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onInitial(fetchCount: Int) {
        binding.responseCode.text = getString(R.string.click_to_get_response_code)
        binding.timesFetched.text = fetchCount.toString()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onSuccess(data: UserResponse, fetchCount: Int) {
        binding.responseCode.text = data.responseCode
        binding.timesFetched.text = fetchCount.toString()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onLoading(isVisible: Boolean) {
        binding.homeProgress.isVisible = isVisible
    }

    private fun onError(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}
