package com.deniz.global.ui.splash

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
import androidx.navigation.fragment.findNavController
import com.deniz.global.databinding.FragmentSplashBinding
import com.deniz.global.model.response.NextResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author: deniz.demirci
 * @date: 20.04.2021
 */

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNextPath()
        setCollectors()
    }

    private fun setCollectors() {
        lifecycleScope.launch {
            viewModel.state.collect { splashViewState ->
                when (splashViewState) {
                    SplashViewState.Initial -> Unit
                    SplashViewState.Loading -> onLoading(true)
                    is SplashViewState.Error -> {
                        onLoading(false)
                        onError(splashViewState.message)
                    }
                    is SplashViewState.Success -> {
                        onLoading(false)
                        onSuccess(splashViewState.data)
                    }
                }
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onSuccess(data: NextResponse) {
        val path = data.nextPath.substringAfter("8000/")
        val direction = SplashFragmentDirections.actionSplashFragmentToHomeFragment(path)
        findNavController().navigate(direction)
    }

    private fun onLoading(isVisible: Boolean) {
        binding.splashProgress.isVisible = isVisible
    }

    private fun onError(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}
