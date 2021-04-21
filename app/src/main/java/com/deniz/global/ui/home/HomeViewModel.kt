package com.deniz.global.ui.home

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.global.data.repositories.UserRepository
import com.deniz.global.extensions.orZero
import com.deniz.global.model.response.UserResponse
import com.deniz.global.model.util.Result
import com.deniz.global.util.storage.Constants
import com.deniz.global.util.storage.Storage
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
class HomeViewModel @Inject constructor(
    private val repository: UserRepository,
    private val storage: Storage
): ViewModel() {

    private val _state = MutableStateFlow<HomeViewState>(
        HomeViewState.Initial(storage.getInt(Constants.TIMES_FETCHED_KEY).orZero())
    )
    val state: StateFlow<HomeViewState> = _state

    fun getUserId(nextPath: String) {
        viewModelScope.launch {
            repository.fetchUser(nextPath).collect { result ->
                when (result) {
                    Result.Loading -> _state.value = HomeViewState.Loading
                    is Result.Success -> onSuccess(result.data)
                    is Result.Error -> _state.value = HomeViewState.Error(result.message)
                }
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onSuccess(data: UserResponse) {
        val fetchCount = storage.getInt(Constants.TIMES_FETCHED_KEY).orZero().inc()
        storage.setInt(
            Constants.TIMES_FETCHED_KEY,
            fetchCount
        )
        _state.value = HomeViewState.Success(data, fetchCount)
    }
}
