package com.deniz.global.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.global.data.repositories.UserRepository
import com.deniz.global.model.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author: deniz.demirci
 * @date: 20.04.2021
 */

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    private val _state = MutableStateFlow<SplashViewState>(SplashViewState.Initial)
    val state: StateFlow<SplashViewState> = _state

    fun getNextPath() {
        viewModelScope.launch {
            repository.fetchNextPath().collect { result ->
                when (result) {
                    Result.Loading -> _state.value = SplashViewState.Loading
                    is Result.Success -> _state.value = SplashViewState.Success(result.data)
                    is Result.Error -> _state.value = SplashViewState.Error(result.message)
                }
            }
        }
    }
}
